package bankingsystem.backend.controller;

import bankingsystem.backend.dto.Constants;
import bankingsystem.backend.dto.Response;
import bankingsystem.backend.dto.UserDto;
import bankingsystem.backend.entity.User;
import bankingsystem.backend.exception.BadRequestException;
import bankingsystem.backend.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/api/createProfile")
public class ProfileController {

    @Autowired
    private UserService userService;

    private final Logger logger = LogManager.getLogger(getClass());

    @PostMapping("/create")
    public ResponseEntity<Response> createUser(@RequestBody @Valid UserDto userDto) {
        try {
            return ResponseEntity.ok(new Response(Constants.SUCCESS, userService.createUser(userDto)));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(Constants.ERROR, e.getMessage()));
        } catch (Exception e) {
            logger.error("error produced during creating user : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(Constants.ERROR, e.getMessage()));
        }
    }




}
