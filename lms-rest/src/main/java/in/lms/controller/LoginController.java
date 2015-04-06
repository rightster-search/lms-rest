package in.lms.controller;

import java.util.Date;
import java.util.List;
import java.util.Set;

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

						EmailUtil.sendEmail(requestBody.getEmailId(), subject,
								emailBody);
						response.setResponse("success");

					}
				} catch (Exception e) {
					logger.info(e.getStackTrace().toString());
					response.setResponse("failure");
				}

			} else {
				response.setResponse("failure");
			}
		} catch (Exception e) {
			response.setResponse("failure");
		}

		return response;
	}

	@RequestMapping(value = CourseRestURIConstants.LOGIN, method = RequestMethod.POST)
	public @ResponseBody
	LoginResponse login(@RequestBody LoginRequest requestBody) throws HackError {
		/*
		 * check whether userSessionId is attached in DB with the username now
		 * check the username and passwd check whether the password is system
		 * generated then in response set the flag.
		 * 
		 * generate the cSessionID save in Db , attach in the response.
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

						String asessionID = MiscellaneousUtil
								.generateSessionID();
						response.setcSessionId(asessionID);
						response.setIsUserGenerated(pwd.getIsSystemGenerated());

						SessionWrapper aSn = new SessionWrapper();
						aSn.setEnvelope(user);
						aSn.setIsValid(true);
						aSn.setSessionID(asessionID);

						// add the session to the user.
						user.getSessionList().add(aSn);
						boolean flag = miscellaneousService
								.updateLoginEnvelope(user);
						if (flag) {
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
							LoginResponse response = new LoginResponse();
							List<PasswordWrapper> pwds = user.getPasswordList();
							PasswordWrapper pwd = pwds.get(pwds.size() - 1);

							String asessionID = MiscellaneousUtil
									.generateSessionID();
							response.setcSessionId(asessionID);
							response.setIsUserGenerated(pwd
									.getIsSystemGenerated());

							SessionWrapper aSn = new SessionWrapper();
							aSn.setEnvelope(user);
							aSn.setIsValid(true);
							aSn.setSessionID(asessionID);

							// remove the user session id as it has been used.
							user.setUserSessionId(null);

							// add the session to the user.
							user.getSessionList().add(aSn);
							boolean flag = miscellaneousService
									.updateLoginEnvelope(user);
							if (flag) {
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
	GenericResponse savePassword(@RequestBody SavePwdRqst requestBody) {

		/*
		 * check whether userSessionId is attached in DB with the username now
		 * check the username and passwd check whether the password is system
		 * generated then in response set the flag.
		 * 
		 * generate the cSessionID save in Db , attach in the response.
		 */
		return null;
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
