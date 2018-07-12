package com.zjut.school.service.impl;

import com.zjut.school.dataobject.OnlineSign;
import com.zjut.school.dataobject.SignIn;
import com.zjut.school.respository.OnlineSignRepository;
import com.zjut.school.respository.SignInRepository;
import com.zjut.school.service.SignInService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by 张璐杰
 * 2018/4/27 18:14
 */

@Service
@Slf4j
public class SignInServiceImpl implements SignInService {

    @Autowired
    private SignInRepository signInRepository;

    @Autowired
    private OnlineSignRepository onlineSignRepository;

    @Override
    @Transactional
    public void saveOnlineSign(String lessonId, double teaLon, double teaLat, Integer distance) {
        OnlineSign onlineSign = new OnlineSign();
        onlineSign.setLessonId(lessonId);
        onlineSign.setTeaLon(teaLon);
        onlineSign.setTeaLat(teaLat);
        onlineSign.setDistance(distance);
        onlineSignRepository.save(onlineSign);
    }

    @Override
    @Transactional
    public void deleteOnlineSign(String lessonId) {
        onlineSignRepository.delete(lessonId);
    }

    @Override
    public List<SignIn> findSigns(String lessonId, Date date, boolean isSign) {
        return signInRepository.findByLessonIdAndSignDateAndIsSign(lessonId, date, isSign);
    }

    @Override
    public OnlineSign getOnlineSign(String lessonId) {
        return onlineSignRepository.findOne(lessonId);
    }

    @Override
    @Transactional
    public void create(SignIn signIn) {
        signInRepository.save(signIn);
    }

    @Override
    public List<SignIn> findByLessonNum(String lessonNum) {
        return signInRepository.findByLessonNum(lessonNum);
    }

    @Override
    @Transactional
    public void saveCallSign(List<SignIn> signList) {
        Date date = new Date();
        String time = new SimpleDateFormat("yyyyMMdd").format(date);
        if (!CollectionUtils.isEmpty(signList)) {
            for (SignIn signIn : signList) {
                signIn.setSignDate(date);
                signIn.setSignId(time+signIn.getLessonId()+signIn.getStuId());
                signInRepository.save(signIn);
            }
        }
    }
}
