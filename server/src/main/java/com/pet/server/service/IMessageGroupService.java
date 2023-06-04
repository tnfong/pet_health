package com.pet.server.service;

import java.util.List;
import java.util.Map;

public interface IMessageGroupService {
    List<Map<String, Object>> list(Integer uid);
    Integer create(Integer uid, Integer idUser);
}
