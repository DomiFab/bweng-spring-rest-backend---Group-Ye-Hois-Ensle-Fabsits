package at.technikum.springrestbackend.mapper;

import at.technikum.springrestbackend.dto.EventDTO;
import at.technikum.springrestbackend.model.EventModel;
import org.springframework.stereotype.Component;


import java.util.UUID;
@Component
public class EventMapper {
    public EventDTO toDTO(EventModel eventModel) {
        //creating a new DTO of Event to assign the values of the Entity to it
        EventDTO newEventDTO = new EventDTO();
        //assigning all Information
        newEventDTO.setAllEventDTO(
                eventModel.getEventID(), eventModel.getCreator(),
                eventModel.getEventName(), eventModel.getEventPicture(),
                eventModel.getEventAdress(), eventModel.getEventDate(),
                eventModel.getEventShortDescription(), eventModel.getEventLongDescription(),
                eventModel.getAttendingUserIDs(), eventModel.getGalleryPictures(),
                eventModel.getEventPosts());
        return newEventDTO;
    }

    public EventModel toEntity(EventDTO eventDTO) {
        //DataBank entry requires the id as a primary key
        if (eventDTO.getEventID() == null) {
            return new EventModel(
                    UUID.randomUUID().toString(),
                    eventDTO.getCreator(), eventDTO.getEventName(),
                    eventDTO.getEventPicture(), eventDTO.getEventLocation(),
                    eventDTO.getEventDate(),
                    eventDTO.getEventShortDescription(),
                    eventDTO.getEventLongDescription());
        }
//      ALTERNATIVELY:
//        if (eventDTO.getEventId() == null) {
//            EventModel newEventModel = new EventModel(
//                          UUID.randomUUID().toString(),
//                          ...
//                      );
//            return newEventModel;
//        }
        return new EventModel(
                eventDTO.getEventID(),
                eventDTO.getCreator(), eventDTO.getEventName(),
                eventDTO.getEventPicture(), eventDTO.getEventLocation(),
                eventDTO.getEventDate(),
                eventDTO.getEventShortDescription(),
                eventDTO.getEventLongDescription());
    }
}
