package in.lms.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Set;

import in.lms.common.EmailClientConstant;
import in.lms.common.GenericResponseConstants;
import in.lms.common.HackError;
import in.lms.common.MiscellaneousUtil;
import in.lms.model.CourseCategoryEnvelope;
import in.lms.model.GeneratePWDRqst;
import in.lms.model.GenericResponse;
import in.lms.model.LoginEnvelope;
import in.lms.model.LoginRequest;
import in.lms.model.LoginResponse;
import in.lms.model.PasswordWrapper;
import in.lms.model.SavePwdRqst;
import in.lms.model.SessionWrapper;
import in.lms.model.UserRole;
import in.lms.model.UserSequence;
import in.lms.service.MiscellaneousService;
import in.mail.EmailUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {
	
	private static String emaiClient = "" ;
	
	static{
		InputStream strm =  LoginController.class.getClassLoader().getResourceAsStream("email-client.txt");
		byte[] bytes = new byte[1028];
		try {
			strm.read(bytes);
			emaiClient = new String(bytes, "UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private MiscellaneousService miscellaneousService;
	private static Object logOutObject = new Object();

	@Autowired(required = true)
	@Qualifier(value = "miscServiceBean")
	public void setMiscellaneousService(MiscellaneousService miscService) {
		this.miscellaneousService = miscService;
	}

	private static final Logger logger = LoggerFactory
			.getLogger(LoginController.class);

	@RequestMapping(value = CourseRestURIConstants.GENERATE_PWD, method = RequestMethod.POST)
	public @ResponseBody
	GenericResponse generatePWD(@RequestBody GeneratePWDRqst requestBody) {
		/*
		 * Generate a password, generate a sessionID, Save it in db
		 * 
		 * Send mail with userSessionID attached with authenticationURL
		 */
		GenericResponse response = new GenericResponse();
		String password = MiscellaneousUtil.generatePassword();
		System.out.println("generate pwd -" + password);
		try {
			String crypticPwd = MiscellaneousUtil.encrypt(password);
			System.out.println("Generated cryptic pwd - " + crypticPwd);
			if (crypticPwd != null) {
				String userSessionID = MiscellaneousUtil.generateSessionID();
				System.out.println("Session ID generated - "+userSessionID);
				String authLink = requestBody.getAuthenticationLink();
				authLink += "?userSessionID=" + userSessionID;

				LoginEnvelope env = new LoginEnvelope();
				UserRole aRole = new UserRole();
				aRole.setRole(requestBody.getUserRole());
				aRole.setUsername(requestBody.getEmailId());
				env.setId(aRole);

				UserSequence sequence = new UserSequence();
				sequence.setUser(env);

				env.setSequenceNo(sequence);

				env.setUserSessionId(userSessionID);

				PasswordWrapper pwd = new PasswordWrapper();
				String dateStr = MiscellaneousUtil.dateTrial(new Date());
				pwd.setCreationDate(dateStr);
				pwd.setEnvelope(env);
				pwd.setPassword(crypticPwd);
				pwd.setIsSystemGenerated(true);

				env.getPasswordList().add(pwd);

				try {
					boolean bool = miscellaneousService.saveLoginEnvelope(env);
					if (bool) {
						String subject = "E-Mantra sign up";
						String emailBody = "<h3><bold>This is a mail from E-Mantra</bold></h3> \n"
								+ "<h4>Your activation link is - <a href=\""
								+ authLink
								+ "\">"
								+ authLink
								+ "</a><h4> \n"
								+ "<h4> password is - "
								+ password
								+ " <h4> \n"
								+ "<h2>Click on the activation Link to log-on - HAPPY LEARNING -<h2>";

						if(emaiClient.equals(EmailClientConstant.GMAIL_SMTP) || emaiClient.isEmpty())
						{
						EmailUtil.sendEmail(requestBody.getEmailId(), subject,
								emailBody);
						}else
						{
							EmailUtil.sendEmailAWS(requestBody.getEmailId(),subject,emailBody);
						}
						response.setResponse("success");

					}
				} catch (Exception e) {
					logger.info(e.getStackTrace().toString());
					response.setResponse("failure");
					response.setErrorMsg(e.getMessage());
				}

			} else {
				response.setResponse("failure");
			}
		} catch (Exception e) {
			response.setResponse("failure");
			response.setErrorMsg(e.getMessage());
		}

		return response;
	}

	@RequestMapping(value = CourseRestURIConstants.LOGIN, method = RequestMethod.POST)
	public @ResponseBody
	LoginResponse login(@RequestBody LoginRequest requestBody) throws Exception {
		/*
		 * check whether userSessionId is attached in DB with the username now
		 * check the username and passwd check whether the password is system
		 * generated then in response set the flag.
		 * 
		 * generate the cSessionID save in Db , attach in the response. need to
		 * return userid in response too.
		 */
		String userSessionID = requestBody.getUserSessionId();
		String username = requestBody.getUsername();
		String password = requestBody.getPassword();
		String role = requestBody.getRole();
		if (userSessionID == null) {
			// check whether trying to hack in.
			synchronized (LoginController.class) {
				LoginEnvelope user = miscellaneousService.getLoginEnvelope(
						username, role);
				if (user != null) {
					String sessionId = user.getUserSessionId();
					if (sessionId != null) {
						logger.info("Probable attempt to hack in with username - "
								+ username);
						// throw an error
						throw new HackError("SomeBody trying to hack-in");
					} else {
						LoginResponse response = new LoginResponse();
						List<PasswordWrapper> pwds = user.getPasswordList();
						PasswordWrapper pwd = pwds.get(pwds.size() - 1);

						//check for password match
						String p1 = MiscellaneousUtil.encrypt(password);
						if(!p1.equals(pwd.getPassword()))
						{
							// throw an error
							throw new HackError("SomeBody trying to hack-in");
						}
						String asessionID = null;

						boolean flag = false;
						List<SessionWrapper> sesnLst = user.getSessionList();
						if (sesnLst != null && !sesnLst.isEmpty()) {
							SessionWrapper sn = sesnLst.get(sesnLst.size() - 1);
							if (sn.getIsValid()) {
								asessionID = sn.getSessionID();
								flag = true;
							}
						}
						boolean flag2 = false;

						if (!flag) {
							asessionID = MiscellaneousUtil.generateSessionID();

							SessionWrapper aSn = new SessionWrapper();
							aSn.setEnvelope(user);
							aSn.setIsValid(true);
							aSn.setSessionID(asessionID);

							// add the session to the user.
							user.getSessionList().add(aSn);
							flag2 = miscellaneousService
									.updateLoginEnvelope(user);

						}
						//write code to match password
						response.setcSessionId(asessionID);
						response.setIsUserGenerated(pwd.getIsSystemGenerated());
						response.setUid(user.getSequenceNo().getId()+"");

						if (flag || (!flag && flag2)) {
							return response;
						} else {
							return null;
						}

					}
				} else {
					throw new HackError("SomeBody trying to hack-in");
				}

				// Set<PasswordWrapper> pwdList = user.getPasswordList();
			}

		} else {
			synchronized (LoginController.class) {
				LoginEnvelope user = miscellaneousService.getLoginEnvelope(
						username, role);
				if (user != null) {
					String sessionId = user.getUserSessionId();
					if (sessionId == null) {
						logger.info("Probable attempt to hack in with username - "
								+ username);
						// throw an error
						throw new HackError("SomeBody trying to hack-in");
					} else {
						if (sessionId.equals(userSessionID)) {
							// Need to move the repeatitive code to a method.

							// remove the user session id as it has been used.
							user.setUserSessionId(null);

							LoginResponse response = new LoginResponse();
							List<PasswordWrapper> pwds = user.getPasswordList();
							PasswordWrapper pwd = pwds.get(pwds.size() - 1);

							//check for password match
							String p1 = MiscellaneousUtil.encrypt(password);
							if(!p1.equals(pwd.getPassword()))
							{
								// throw an error
								throw new HackError("SomeBody trying to hack-in");
							}
							String asessionID = null;

							boolean flag = false;
							List<SessionWrapper> sesnLst = user
									.getSessionList();
							if (sesnLst != null && !sesnLst.isEmpty()) {
								SessionWrapper sn = sesnLst
										.get(sesnLst.size() - 1);
								if (sn.getIsValid()) {
									asessionID = sn.getSessionID();
									flag = true;
								}
							}
							boolean flag2 = false;

							if (!flag) {
								asessionID = MiscellaneousUtil
										.generateSessionID();

								SessionWrapper aSn = new SessionWrapper();
								aSn.setEnvelope(user);
								aSn.setIsValid(true);
								aSn.setSessionID(asessionID);

								// add the session to the user.
								user.getSessionList().add(aSn);

							}

							flag2 = miscellaneousService
									.updateLoginEnvelope(user);

							response.setcSessionId(asessionID);
							response.setIsUserGenerated(pwd
									.getIsSystemGenerated());
							response.setUid(user.getSequenceNo().getId()+"");

							if (flag || (!flag && flag2)) {
								return response;
							} else {
								return null;
							}

						} else {
							throw new HackError("SomeBody trying to hack-in");
						}
					}
				} else {
					throw new HackError("SomeBody trying to hack-in");
				}

			}
		}
	}

	@RequestMapping(value = CourseRestURIConstants.SAVEPWD, method = RequestMethod.POST)
	public @ResponseBody
	GenericResponse savePassword(@RequestBody SavePwdRqst requestBody) throws Exception {

		/*
		 * with userid and old password get the user object, now make the old
		 * password invalid, add the new password , save the user instance in db
		 * , if its saved return success.
		 */
		GenericResponse response = new GenericResponse();

		String uid = requestBody.getUid();
		if (uid != null) {
			LoginEnvelope user = miscellaneousService
					.getLoginEnvelopeFromUID(uid);
			if (user != null) {
				List<PasswordWrapper> pwds = user.getPasswordList();
				if (pwds != null && !pwds.isEmpty()) {
					PasswordWrapper oldPwd = pwds.get(pwds.size() - 1);
					String p1 = MiscellaneousUtil.encrypt(requestBody.getOldPwd());
					if (p1.equals(oldPwd.getPassword())) {
						PasswordWrapper newPwd = new PasswordWrapper();
						newPwd.setCreationDate(MiscellaneousUtil
								.dateTrial(new Date()));
						newPwd.setEnvelope(user);
						newPwd.setPassword(MiscellaneousUtil.encrypt(requestBody.getNewPWd()));
						newPwd.setIsSystemGenerated(false);
						user.getPasswordList().add(newPwd);

						boolean flag = miscellaneousService
								.updateLoginEnvelope(user);

						if (flag) {
							response.setResponse(GenericResponseConstants.SUCCESS);
						} else {
							response.setResponse(GenericResponseConstants.FAILURE);
							response.setErrorMsg("New Password couldnot be saved in DB");
						}

					} else {
						response.setResponse(GenericResponseConstants.FAILURE);
						response.setErrorMsg("OLD Password sent in request doesnot match old password present in DB");
					}
				} else {
					response.setResponse(GenericResponseConstants.FAILURE);
					response.setErrorMsg("OLD Password not present in DB");
				}
			} else {
				response.setResponse(GenericResponseConstants.FAILURE);
				response.setErrorMsg("Bogus Call as request doesnot have a valid uid!!");
			}
		} else {
			response.setResponse(GenericResponseConstants.FAILURE);
			response.setErrorMsg("Bogus Call as request doesnot have uid!!");
		}

		return response;
	}

	/*
	 * @RequestMapping(value = "/{cartId}", method = RequestMethod.GET) public
	 * 
	 * @ResponseBody Cart read(@PathVariable(value = "cartId") String cartId) {
	 * return cartService.read(cartId); }
	 */

	@RequestMapping(value = CourseRestURIConstants.LOGOUT, method = RequestMethod.GET)
	public @ResponseBody
	Boolean logout(@RequestParam(value = "sessionID") String sessionId) {

		/*
		 * check whether userSessionId is attached in DB with the username now
		 * check the username and passwd check whether the password is system
		 * generated then in response set the flag.
		 * 
		 * generate the cSessionID save in Db , attach in the response.
		 */
		if (sessionId != null) {
			synchronized (logOutObject) {

				SessionWrapper aSn = miscellaneousService
						.getSessionWrapper(sessionId);
				if (aSn != null) {
					if (aSn.getIsValid()) {
						aSn.setIsValid(false);
						boolean flag = miscellaneousService
								.updateSessionWrapper(aSn);
						if (flag)
							return true;
						else
							return false;
					} else {
						// throw an exception saying already session invalid
						return false;
					}
				} else {
					return false;
				}

			}
		} else {
			// should throw an exception
			return false;
		}

		// return null;
	}

	public static void main(String[] args) {
		String receiver = "arkadutta0504@gmail.com";
		String subject = "E-Mantra sign up";

		String authLink = "http://127.0.0.1:8080/lms-rest/authneticate?usersessionId=qwerty1234";
		String password = "arka1234";

		String emailBody = "<h3><bold>This is a mail from E-Mantra</bold></h3> \n"
				+ "<h4>Your activation link is - <a href=\""
				+ authLink
				+ "\">"
				+ authLink
				+ "</a><h4> \n"
				+ "<h4> password is - "
				+ password
				+ " <h4> \n"
				+ "<h2>Click on the activation Link to log-on - HAPPY LEARNING -<h2>";

		EmailUtil.sendEmail(receiver, subject, emailBody);

	}

}
