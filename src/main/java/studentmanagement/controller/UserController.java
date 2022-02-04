package studentmanagement.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import studentmanagement.model.LoginBean;
import studentmanagement.model.SearchBean;
import studentmanagement.model.UserBean;
import studentmanagement.persistant.dto.UserRequestDto;
import studentmanagement.persistant.dto.UserResponseDto;
import studentmanagement.service.UserServiceImpl;

@Controller
public class UserController {

	@Autowired
	private UserServiceImpl uDAO;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView login() {
		return new ModelAndView("LGN001", "loginBean", new LoginBean());
	}

	@RequestMapping(value = "/welcome")
	public String mainPage() {
		return "M00001";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@ModelAttribute("loginBean") @Validated LoginBean loginbean, BindingResult br, ModelMap model,
			HttpSession session) {

		if (br.hasErrors()) {
			return "LGN001";
		}

		UserRequestDto uDTO = new UserRequestDto();
		uDTO.setId(loginbean.getId());

		List<UserResponseDto> userList = uDAO.selectOne(uDTO);

		if (userList.size() == 0) {
			model.addAttribute("Error", "User ID not found !!!");
			return "LGN001";
		} else {
			if (userList.get(0).getPassword().equals(loginbean.getPassword())) {
				session.setAttribute("userId", userList.get(0).getId());
				session.setAttribute("userName", userList.get(0).getName());

				return "M00001";
			} else {
				model.addAttribute("Error", "Incorrect Password .... Try Again !!!");
				return "LGN001";
			}
		}
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

	@RequestMapping(value = "/setupUser", method = RequestMethod.GET)
	public ModelAndView setupUser(@ModelAttribute("Mes") String Mes,@ModelAttribute("Success")String Success, ModelMap model) {
		model.addAttribute("Mes", Mes);
		model.addAttribute("Success",Success);
		return new ModelAndView("USR001", "sBean", new SearchBean());
	}

	@RequestMapping(value = "/searchUser", method = RequestMethod.GET)
	public String searchUser(@ModelAttribute("sBean") SearchBean searchBean, ModelMap model) {

		List<UserResponseDto> userList = new ArrayList<>();
		UserRequestDto uDTO = new UserRequestDto();

		uDTO.setId(searchBean.getUserId());
		uDTO.setName(searchBean.getUserName());

		if(!uDTO.getId().equals("") || !uDTO.getName().equals("")) {
			userList = uDAO.selectOne(uDTO);
		}else {
			 userList = uDAO.selectAll();
		}
		
		if (userList.size() == 0) {
			model.addAttribute("Error", "No User Found !!!");
		} else {
			model.addAttribute("userList", userList);
			model.addAttribute("Success", "Search done Successfully");
		}
		return "USR001";
	}

	@RequestMapping(value = "/setupAddUser", method = RequestMethod.GET)
	public ModelAndView addUser() {

		return new ModelAndView("USR002", "uBean", new UserBean());
	}

	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public String addUser(@ModelAttribute("uBean") @Validated UserBean userBean, BindingResult br, ModelMap model) {

		if (br.hasErrors()) {
			return "USR002";
		}
		UserRequestDto uDTO = new UserRequestDto();

		if (userBean.getPassword().equals(userBean.getConfirm())) {

			uDTO.setId(userBean.getId());
			

			List<UserResponseDto> checkUserList = uDAO.selectOne(uDTO);

			if (checkUserList.size() != 0) {
				model.addAttribute("Error", "User ID has been already used..... Choose another user ID");
			} else {
				uDTO.setName(userBean.getName());
				uDTO.setPassword(userBean.getPassword());
				int i = uDAO.insert(uDTO);

				if (i > 0) {
					model.addAttribute("Success", "User registered Successfully");
				} else {
					model.addAttribute("Error", "User register fail !!!");
				}
			}
		} else {
			model.addAttribute("Error", "Passwords didn't match !!!");
		}
		return "USR002";
	}

	@RequestMapping(value = "/setupUpdateUser/{id}", method = RequestMethod.GET)
	public ModelAndView setupUpdateUser(@PathVariable String id) {
		UserRequestDto uDTO = new UserRequestDto();
		uDTO.setId(id);

		List<UserResponseDto> list = uDAO.selectOne(uDTO);
		UserBean userBean = new UserBean();
		for (UserResponseDto upDTO : list) {
			userBean.setId(upDTO.getId());
			userBean.setName(upDTO.getName());
			userBean.setPassword(upDTO.getPassword());
			userBean.setConfirm(upDTO.getPassword());
		}
		return new ModelAndView("USR002-01", "uBean", userBean);
	}

	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	public String updateUser(@ModelAttribute("uBean") @Validated UserBean userBean, BindingResult br, ModelMap model) {

		if (br.hasErrors()) {
			return "USR002-01";
		}

		UserRequestDto uDTO = new UserRequestDto();
		if (userBean.getPassword().equals(userBean.getConfirm())) {
			uDTO.setId(userBean.getId());
			uDTO.setName(userBean.getName());
			uDTO.setPassword(userBean.getPassword());

			int i = uDAO.update(uDTO);

			if (i > 0) {
				model.addAttribute("Success", "User Updated Successfully");
			}
		} else {
			model.addAttribute("Error", "Password didn't match !!!");
		}
		return "USR002-01";
	}

	@RequestMapping(value = "/deleteUser/{id}", method = RequestMethod.GET)
	public String deleteUser(@PathVariable String id, RedirectAttributes redir, ModelMap model, HttpSession session) {
		UserRequestDto uDTO = new UserRequestDto();
		uDTO.setId(id);

		if (uDTO.getId().equals(session.getAttribute("userId"))) {
			redir.addAttribute("Mes", "Cann't delete this current login user !!!");
		} else {

			int i = uDAO.delete(uDTO);

			if (i > 0) {
				redir.addAttribute("Success", "Deleted " + uDTO.getId() + " Successfully");
			}
			
		}
		return "redirect:/setupUser/";
	}
}