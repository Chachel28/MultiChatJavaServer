package net.juanxxiii.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalTime;

@Data
public class Message implements Serializable {
    private String username;
    private String message;
    private LocalTime timestamp;
}