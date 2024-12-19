package com.example.LMS.repositories;
import com.example.LMS.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface profileRepository extends JpaRepository<Profile, Integer>
{
    //    Optional<Profile> findByUserId(Integer Id);
    Profile  getProfileById(int user_id);


}