package org.booking.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.booking.domain.Notification;
import org.booking.repository.NotificationRepository;
import org.booking.utils.PaginationUtil;

/**
 * REST controller for managing Notification
 */
@RestController
@RequestMapping("/api")
public class NotificationController {

	@Autowired
	private NotificationRepository notificationRepository;

    /**
     * POST  /notifications -> Create a new notification
     */
	@Transactional
    @RequestMapping(value = "/notifications",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Notification> create(@Valid @RequestBody Notification notification) throws URISyntaxException {
        Notification result = notificationRepository.save(notification);
        return ResponseEntity.created(new URI("/api/notifications/" + result.getId())).body(result);
    }

    /**
     * PUT  /notifications -> Updates an existing notification
     */
	@Transactional
    @RequestMapping(value = "/notifications/{id}",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Notification> update(@Valid @RequestBody Notification notification, @PathVariable Long id) throws URISyntaxException {
		Notification existing = notificationRepository.findOne(id);
		Notification result;
		if(existing!=null){
			notification.setId(id);
	        result = notificationRepository.save(notification);
	        return new ResponseEntity<>(result,HttpStatus.OK);
		}else{
	        result = notificationRepository.save(notification);
	        return ResponseEntity.created(new URI("/api/notifications/" + result.getId())).body(result);
		}
    }

    /**
     * GET  /notifications -> get all the notifications per one user
     */
    @RequestMapping(value = "/notifications",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Notification>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit, @RequestParam(value="userId",required=true) Long userId)
        throws URISyntaxException {
        Page<Notification> page = notificationRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        List<Notification> filtered = page.getContent().stream().filter(notif->notif.getNotifiedUserId().equals(userId)).collect(Collectors.toList());
        return new ResponseEntity<>(filtered, HttpStatus.OK);
    }

    /**
     * GET  /notifications/:id -> get the "id" notification
     */
    @RequestMapping(value = "/notifications/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Notification> get(@PathVariable Long id) {
        return Optional.ofNullable(notificationRepository.findOne(id))
            .map(notification -> new ResponseEntity<>(notification,HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    /**
     * DELETE  /notifications/:id -> delete the "id" notification
     */
    @Transactional
    @RequestMapping(value = "/notifications/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Notification> delete(@PathVariable Long id) {
        notificationRepository.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
