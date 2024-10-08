package at.technikum.springrestbackend.services;

import at.technikum.springrestbackend.dto.ForumPostDTO;
import at.technikum.springrestbackend.exception.EntityNotFoundException;
import at.technikum.springrestbackend.model.ForumPostModel;
import at.technikum.springrestbackend.repository.ForumPostRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ForumPostServices {
    private final ForumPostRepository postRepository;

    public ForumPostServices(ForumPostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public boolean idExists(String id){
        return postRepository.existsById(id);
    }
    public ForumPostModel find(String id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new EntityExistsException("Post not found with id: " + id));
    }

    public List<ForumPostModel> findAll (){
        return postRepository.findAll();
    }

    public ForumPostModel save(ForumPostModel forumPostModel){
        return postRepository.save(forumPostModel);
    }

    public ForumPostModel update(String id, ForumPostDTO forumPostDTOupdated){

        //catching the case when an entity with the id does not exist
        if (!idExists(id)){
            throw new EntityNotFoundException("Forum Post with provided ID [" + id + "] not found.");
        }

        //get the existing Post from the DB and THEN set new values
        ForumPostModel existingPost = postRepository.findById(id).orElseThrow();

        //leaving all the fields in BUT: author, eventID and media (?) are redundant
        existingPost.setAllEntity(
                id,
                forumPostDTOupdated.getTitle(),
                forumPostDTOupdated.getAuthor(),
                forumPostDTOupdated.getContent(),
                forumPostDTOupdated.getMediaPlaceHolder()
        );

        return postRepository.save(existingPost);
    }
}

