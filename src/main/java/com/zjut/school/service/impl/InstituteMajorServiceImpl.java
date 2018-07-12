package com.zjut.school.service.impl;

import com.zjut.school.VO.ParamVO;
import com.zjut.school.dataobject.ClassInfo;
import com.zjut.school.dataobject.InstituteInfo;
import com.zjut.school.dataobject.MajorInfo;
import com.zjut.school.dto.ParamDTO;
import com.zjut.school.enums.ResultEnum;
import com.zjut.school.exception.SchoolException;
import com.zjut.school.respository.ClassInfoRepository;
import com.zjut.school.respository.InstituteInfoRepository;
import com.zjut.school.respository.MajorInfoRepository;
import com.zjut.school.service.InstituteMajorService;
import com.zjut.school.utils.ExcelUtil;
import com.zjut.school.utils.FormatUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 张璐杰
 * 2018/4/16 13:17
 */

@Service
@Slf4j
public class InstituteMajorServiceImpl implements InstituteMajorService {

    @Autowired
    private InstituteInfoRepository instituteInfoRepository;

    @Autowired
    private MajorInfoRepository majorInfoRepository;

    @Autowired
    private ClassInfoRepository classInfoRepository;

    @Override
    public InstituteInfo findInstitute(String instituteId) {
        return instituteInfoRepository.findOne(instituteId);
    }

    @Override
    public ClassInfo findClass(String classNum, String major, String institute) {
        return classInfoRepository.findByClassNumAndMajorIdAndInstituteId(classNum, major, institute);
    }

    @Override
    public String getInstituteId(String instituteName) {
        InstituteInfo instituteInfo =  instituteInfoRepository.findByInstituteName(instituteName);
        if (instituteInfo == null) {
            throw new SchoolException(ResultEnum.INSTITUTE_ERROR);
        }

        return instituteInfo.getInstituteId();
    }

    @Override
    public String getMajorId(String instituteId, String majorName) {

        MajorInfo majorInfo = majorInfoRepository.findByInstituteIdAndMajorName(instituteId, majorName);
        if (majorInfo == null) {
            return null;
        }

        return majorInfo.getMajorId();
    }

    @Override
    public MajorInfo findMajor(String majorId) {
        return majorInfoRepository.findOne(majorId);
    }

    @Override
    public List<InstituteInfo> importInstituteData(File file) {

        Workbook wb = ExcelUtil.workbookType(file);
        Sheet sheet = wb.getSheetAt(0);

        List<InstituteInfo> instituteInfoList = new ArrayList<>();
        for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
            if (i==0) {
                continue;
            }
            Row row = sheet.getRow(i);
            try {
                InstituteInfo instituteInfo = new InstituteInfo();
                Integer institute = (int)Math.round(row.getCell(0).getNumericCellValue());
                String instituteId = FormatUtil.FormatFour(institute);
                instituteInfo.setInstituteId(instituteId);

                String instituteName = row.getCell(1).getStringCellValue();
                instituteInfo.setInstituteName(instituteName);

                if (row.getCell(2) != null) {
                    String description = row.getCell(2).getStringCellValue();
                    instituteInfo.setDescription(description);
                }
                instituteInfoList.add(instituteInfo);
            } catch (Exception e) {
                throw new SchoolException(ResultEnum.EXCEL_ERROR.getCode(),"表格属性格式不正确");
            }
        }
        return instituteInfoList;
    }

    @Override
    public void create(InstituteInfo instituteInfo) {
        instituteInfoRepository.save(instituteInfo);
    }

    @Override
    public void create(MajorInfo majorInfo) {
        majorInfoRepository.save(majorInfo);
    }

    @Override
    public InstituteInfo findByClass(String classId) {
        ClassInfo classInfo = classInfoRepository.findOne(classId);
        InstituteInfo instituteInfo = new InstituteInfo();
        if (classInfo != null) {
            instituteInfo = instituteInfoRepository.findOne(classInfo.getInstituteId());
        }
        return instituteInfo;
    }

    @Override
    public List<ParamDTO> getSelect() {
        List<ParamDTO> instituteList = instituteInfoRepository.findAll()
                .stream().map(e -> {
                    ParamDTO paramDTO = new ParamDTO();
                    paramDTO.setLabel(e.getInstituteName());
                    paramDTO.setValue(e.getInstituteId());
                    return paramDTO;
                }).collect(Collectors.toList());
        for (ParamDTO paramDTO : instituteList) {
            List<ParamDTO> majorList = majorInfoRepository
                    .findByInstituteId(paramDTO.getValue())
                    .stream().map(e -> {
                        ParamDTO major = new ParamDTO();
                        major.setValue(e.getMajorId());
                        major.setLabel(e.getMajorName());
                        return major;
                    }).collect(Collectors.toList());
            for (ParamDTO major : majorList) {
                List<ParamVO> classList = classInfoRepository
                        .findByMajorIdAndAndInstituteId(major.getValue(), paramDTO.getValue())
                        .stream().map(e -> {
                            ParamVO paramVO = new ParamVO();
                            paramVO.setLabel(e.getClassNum());
                            paramVO.setValue(e.getClassNum());
                            return paramVO;
                        }).collect(Collectors.toList());
                major.setChildren(classList);
            }
            paramDTO.setChildren(majorList);
        }
        return instituteList;
    }

    @Override
    public List<MajorInfo> importMajorData(File file) {
        Workbook wb = ExcelUtil.workbookType(file);
        Sheet sheet = wb.getSheetAt(0);

        List<MajorInfo> majorInfoList = new ArrayList<>();
        for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
            if (i==0) {
                continue;
            }
            Row row = sheet.getRow(i);
            try {
                MajorInfo majorInfo = new MajorInfo();
                Integer major = (int)Math.round(row.getCell(0).getNumericCellValue());
                String majorId = FormatUtil.FormatFour(major);
                majorInfo.setMajorId(majorId);

                String instituteId = row.getCell(1).getStringCellValue();
                majorInfo.setInstituteId(instituteId);

                String majorName = row.getCell(2).getStringCellValue();
                majorInfo.setMajorName(majorName);

                majorInfoList.add(majorInfo);
            } catch (Exception e) {
                throw new SchoolException(ResultEnum.EXCEL_ERROR.getCode(),"表格属性格式不正确");
            }
        }
        return majorInfoList;
    }
}
