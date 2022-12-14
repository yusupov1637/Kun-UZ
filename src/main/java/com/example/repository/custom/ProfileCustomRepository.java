package com.example.repository.custom;

import com.example.dto.profile.ProfileFilterDTO;
import com.example.entity.ProfileEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProfileCustomRepository {

    @Autowired
    private EntityManager entityManager;

    public List<ProfileEntity> filter(ProfileFilterDTO filter,int page,int size) {
        StringBuilder builder = new StringBuilder("SELECT a FROM ProfileEntity a ");
        Map<String,Object> params=new HashMap<>();

        if (filter.getVisible() != null) {
            builder.append(" where a.visible = ").append(filter.getVisible());
        } else {
            builder.append(" where a.visible = true ");
        }

        if (filter.getName() != null) {
            builder.append(" And a.name =:name");
            params.put("name",filter.getName());
        }
        if (filter.getSurname() != null) {
            builder.append(" And a.surname =:surname");
            params.put("surname",filter.getSurname());
        }
        if (filter.getPhone() != null) {
            builder.append(" And a.phone =:phone");
            params.put("phone",filter.getPhone());
        }
        if (filter.getEmail() != null) {
            builder.append(" And a.email =:email");
            params.put("email",filter.getEmail());
        }
        if (filter.getStatus() != null) {
            builder.append(" And a.status =:status");
            params.put("status",filter.getStatus());
        }
        if (filter.getRole() != null) {
            builder.append(" And a.role =:role");
            params.put("role",filter.getRole());
        }
        if (filter.getFromDate() != null && filter.getToDate() != null) {
            builder.append(" And cast(a.createdDate as date) between :fromDate and :toDate ");
            params.put("fromDate", filter.getFromDate().atStartOfDay());
            params.put("toDate", filter.getToDate().atTime(LocalTime.MIN));
        } else if (filter.getFromDate() != null) { // from
            builder.append(" And a.createdDate > :fromDate ");
            params.put("fromDate", filter.getFromDate().atStartOfDay());
        } else if (filter.getToDate() != null) {
            builder.append(" And a.createdDate < :toDate ");
            params.put("toDate", filter.getToDate().atTime(LocalTime.MIN));
        }


        Query query = entityManager.createQuery(builder.toString());
        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            query.setParameter(entry.getKey(),entry.getValue());
        }
        List<ProfileEntity> profileEntities = query.getResultList();

        return profileEntities;


    }
    public List<ProfileEntity> getAll() {
        Query query = this.entityManager.createQuery("SELECT p From ProfileEntity as p");
        List profileEntities = query.getResultList();
        return profileEntities;
    }
    public List<ProfileEntity> getAllNative() {
        Query query = entityManager.createNativeQuery("SELECT * FROM profile ", ProfileEntity.class);
        List profileEntities = query.getResultList();
        return profileEntities;
    }
}
