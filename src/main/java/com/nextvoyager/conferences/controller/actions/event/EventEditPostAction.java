package com.nextvoyager.conferences.controller.actions.event;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.service.EventService;
import com.nextvoyager.conferences.util.validation.ParameterValidator;
import com.nextvoyager.conferences.util.validation.ValidateObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDateTime;

import static com.nextvoyager.conferences.controller.actions.ControllerActionConstants.*;
import static com.nextvoyager.conferences.util.validation.ValidateRegExp.REGEXP_DATETIME;
import static com.nextvoyager.conferences.util.validation.ValidateRegExp.REGEXP_ID;

/**
 * Edit event.
 * Path "/event/edit".
 * POST Method.
 *
 * @author Stanislav Bozhevskyi
 */
public class EventEditPostAction implements ControllerAction {

    private static final ValidateObject[] validateObjects = {
            new ValidateObject(PARAM_EVENT_ID, REGEXP_ID),
            new ValidateObject(PARAM_EVENT_NAME),
            new ValidateObject(PARAM_EVENT_PLACE),
            new ValidateObject(PARAM_EVENT_BEGIN_DATE, REGEXP_DATETIME),
            new ValidateObject(PARAM_EVENT_END_DATE, REGEXP_DATETIME),
            new ValidateObject(PARAM_EVENT_DESCRIPTION,true),
            new ValidateObject(PARAM_EVENT_PARTICIPANTS_CAME,REGEXP_ID)
    };

    private final EventService eventService;

    public EventEditPostAction(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        ParameterValidator.validate(req,validateObjects);

        Integer idParam = Integer.valueOf(req.getParameter(PARAM_EVENT_ID));
        String nameParam = req.getParameter(PARAM_EVENT_NAME);
        String placeParam = req.getParameter(PARAM_EVENT_PLACE);
        LocalDateTime beginDateParam = LocalDateTime.parse(req.getParameter(PARAM_EVENT_BEGIN_DATE));
        LocalDateTime endDateParam = LocalDateTime.parse(req.getParameter(PARAM_EVENT_END_DATE));
        String descriptionParam = req.getParameter(PARAM_EVENT_DESCRIPTION);
        String participantsCameParam = req.getParameter(PARAM_EVENT_PARTICIPANTS_CAME);
        String sendNotification = req.getParameter(PARAM_EVENT_SEND_NOTIFICATION);

        Event event = new Event();
        event.setId(idParam);
        event.setName(nameParam);
        event.setPlace(placeParam);
        event.setBeginDate(beginDateParam);
        event.setEndDate(endDateParam);
        event.setDescription(descriptionParam);
        event.setParticipantsCame(Integer.valueOf(participantsCameParam));

        eventService.update(event, sendNotification != null);

        return PREFIX_PATH + "/event/view?eventID=" + event.getId();
    }
}
