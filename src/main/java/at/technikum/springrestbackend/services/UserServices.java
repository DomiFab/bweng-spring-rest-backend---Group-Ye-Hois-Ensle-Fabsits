package at.technikum.springrestbackend.services;
import at.technikum.springrestbackend.dto.UserDTO;
import at.technikum.springrestbackend.exception.EntityNotFoundException;
import at.technikum.springrestbackend.mapper.UserMapper;
import at.technikum.springrestbackend.model.UserModel;
import at.technikum.springrestbackend.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServices {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServices(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }
    public boolean idExists(String id){
        return userRepository.existsById(id);
    }

    public boolean usernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public UserModel find(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityExistsException("User not found with id: " + id));
    }

    public UserModel findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username));
    }

    public List<UserModel> findAll (){
        return userRepository.findAll();
    }

//    public List<UserModel> findAllUserOfEvent (Event event) {
//        //TODO: list of users of a certain event
//
//        return userRepository.findAllById(event.getID);
//    }

    public UserModel save(UserModel userModel){
        return userRepository.save(userModel);
    }

    public UserDTO registerUser(UserDTO userDTO) {
        // Check if username or email exists
        if (usernameExists(userDTO.getUsername())) {
            throw new EntityExistsException("Username already exists: " + userDTO.getUsername());
        }
        if (emailExists(userDTO.getEmail())) {
            throw new EntityExistsException("Email already exists: " + userDTO.getEmail());
        }

        // Convert DTO to entity and save the user
        UserModel newUser = userMapper.toEntity(userDTO);
        userRepository.save(newUser);
        return userMapper.toDTO(newUser);
    }

    public UserModel update(String id, UserDTO userDTOupdate){

        //catching the case when an entity with the id does not exist
        if (!idExists(id)){
            throw new EntityNotFoundException("User with provided ID [" + id + "] not found.");
        }

        //get the existing User from the DB and THEN set new values
        UserModel existingUser = userRepository.findById(id).orElseThrow();

        existingUser.setAttendingEvents(userDTOupdate.getAttendingEvents());
        existingUser.setProfilePicture(userDTOupdate.getProfilePicture());
        existingUser.setProfileDescription(userDTOupdate.getProfileDescription());
        existingUser.setUsername(userDTOupdate.getUsername());
        existingUser.setEmail(userDTOupdate.getEmail());

        return userRepository.save(existingUser);
    }
}
