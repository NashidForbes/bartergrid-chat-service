package com.bartergrid.chatservice.query.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * REST controller for querying Hedera Consensus Service audit log entries.
 * Provides endpoints for retrieving topic messages by topic ID, event/entity filters,
 * and by specific sequence number.
 */
@Slf4j
@RestController
@RequestMapping("/chatchannel")
public class ChatQueryController {

    public ChatQueryController() {

    }

}
