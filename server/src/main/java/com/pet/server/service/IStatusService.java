package com.pet.server.service;

import com.pet.server.entity.Status;

import java.util.List;

public interface IStatusService {

    List<Status> getByTable(String name);

}
