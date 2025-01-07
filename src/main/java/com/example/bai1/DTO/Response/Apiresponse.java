/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.bai1.DTO.Response;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author Admin
 * @param <T>
 */
@Builder
@Data

public class Apiresponse<T> {
    private int code=200;
    private String message;
    private T result;
}
