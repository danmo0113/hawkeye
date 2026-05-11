package com.ruoyi.hawkeye.question.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.hawkeye.knowledgePoint.domain.HawkeyeKnowledgePoint;
import com.ruoyi.hawkeye.knowledgePoint.mapper.HawkeyeKnowledgePointMapper;
import com.ruoyi.hawkeye.questionKp.domain.HawkeyeQuestionKp;
import com.ruoyi.hawkeye.questionKp.mapper.HawkeyeQuestionKpMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.hawkeye.question.mapper.HawkeyeQuestionMapper;
import com.ruoyi.hawkeye.question.domain.HawkeyeQuestion;
import com.ruoyi.hawkeye.question.service.IHawkeyeQuestionService;

/**
 * 题库题目Service业务层处理
 * 
 * @author 墨
 * @date 2026-04-27
 */
@Service
public class HawkeyeQuestionServiceImpl implements IHawkeyeQuestionService 
{
    @Autowired
    private HawkeyeQuestionMapper hawkeyeQuestionMapper;

    @Autowired
    private HawkeyeQuestionKpMapper hawkeyeQuestionKpMapper;

    @Autowired
    private HawkeyeKnowledgePointMapper hawkeyeKnowledgePointMapper;

    /**
     * 查询题库题目
     * 
     * @param questionId 题库题目主键
     * @return 题库题目
     */
    @Override
    public HawkeyeQuestion selectHawkeyeQuestionByQuestionId(Long questionId)
    {
        HawkeyeQuestion hawkeyeQuestion = hawkeyeQuestionMapper.selectHawkeyeQuestionByQuestionId(questionId);
        hawkeyeQuestion = this.getQuestionDetails(hawkeyeQuestion);

        return hawkeyeQuestion;
    }

    /**
     * 查询详细题目列表
     *
     * @param hawkeyeQuestion 题库题目
     * @return 题库题目
     */
    @Override
    public void selectHawkeyeQuestionListExport(HttpServletResponse response, HawkeyeQuestion hawkeyeQuestion) {
        // 1. 在内存中创建一个 Excel 文件对象 (这里使用 XSSF 对应 .xlsx 格式)
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 2. 创建一个 Sheet 页
        XSSFSheet sheet = workbook.createSheet("员工信息");
        // 3. 创建表头行
        XSSFRow headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("姓名");
        headerRow.createCell(1).setCellValue("城市");
        // 4. 创建数据行并填充数据
        XSSFRow dataRow1 = sheet.createRow(1);
        dataRow1.createCell(0).setCellValue("张三");
        dataRow1.createCell(1).setCellValue("北京");
        XSSFRow dataRow2 = sheet.createRow(2);
        dataRow2.createCell(0).setCellValue("李四");
        dataRow2.createCell(1).setCellValue("南京");
        // 6. 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("UTF-8");

        String fileName = null;
        try {
            fileName = URLEncoder.encode("用户信息导出", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-Disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

            // 7. 将 workbook 写入 response 的输出流
            workbook.write(response.getOutputStream());
            response.getOutputStream().flush();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


//        List<HawkeyeQuestion> hawkeyeQuestionList = hawkeyeQuestionMapper.selectHawkeyeQuestionList(hawkeyeQuestion);
//        List<HawkeyeQuestion> newHawkeyeQuestion = new ArrayList<>();
//        for (HawkeyeQuestion question : hawkeyeQuestionList) {
//            question = this.getQuestionDetails(question);
//            newHawkeyeQuestion.add(question);
//        }
//        return newHawkeyeQuestion;
    }

    /**
     * 查询题库题目列表
     * 
     * @param hawkeyeQuestion 题库题目
     * @return 题库题目
     */
    @Override
    public List<HawkeyeQuestion> selectHawkeyeQuestionList(HawkeyeQuestion hawkeyeQuestion)
    {
        return hawkeyeQuestionMapper.selectHawkeyeQuestionList(hawkeyeQuestion);
    }

    /**
     * 新增题库题目
     * 
     * @param hawkeyeQuestion 题库题目
     * @return 结果
     */
    @Override
    public int insertHawkeyeQuestion(HawkeyeQuestion hawkeyeQuestion)
    {
        hawkeyeQuestion.setCreateTime(DateUtils.getNowDate());
        hawkeyeQuestion = this.processingData(hawkeyeQuestion);
        int i = hawkeyeQuestionMapper.insertHawkeyeQuestion(hawkeyeQuestion);
        //插入题目-考点关联表
        this.addQuestionKp(hawkeyeQuestion);
        return i;
    }




    /**
     * 修改题库题目
     * 
     * @param hawkeyeQuestion 题库题目
     * @return 结果
     */
    @Override
    public int updateHawkeyeQuestion(HawkeyeQuestion hawkeyeQuestion)
    {
        hawkeyeQuestion.setUpdateTime(DateUtils.getNowDate());
        hawkeyeQuestion = this.processingData(hawkeyeQuestion);
        int i = hawkeyeQuestionMapper.updateHawkeyeQuestion(hawkeyeQuestion);
        //插入题目-考点关联表
        this.addQuestionKp(hawkeyeQuestion);
        return i;
    }

    /**
     * 批量删除题库题目
     * 
     * @param questionIds 需要删除的题库题目主键
     * @return 结果
     */
    @Override
    public int deleteHawkeyeQuestionByQuestionIds(Long[] questionIds)
    {
        return hawkeyeQuestionMapper.deleteHawkeyeQuestionByQuestionIds(questionIds);
    }

    /**
     * 删除题库题目信息
     * 
     * @param questionId 题库题目主键
     * @return 结果
     */
    @Override
    public int deleteHawkeyeQuestionByQuestionId(Long questionId)
    {
        return hawkeyeQuestionMapper.deleteHawkeyeQuestionByQuestionId(questionId);
    }
    private void addQuestionKp(HawkeyeQuestion hawkeyeQuestion) {
        if (hawkeyeQuestion.getKpIds() != null) {
            for (Long kpId : hawkeyeQuestion.getKpIds()) {
                HawkeyeQuestionKp questionKp = new HawkeyeQuestionKp();
                questionKp.setKpId(kpId);
                questionKp.setQuestionId(hawkeyeQuestion.getQuestionId());
                hawkeyeQuestionKpMapper.insertHawkeyeQuestionKp(questionKp);
            }
        }
    }

    private HawkeyeQuestion processingData(HawkeyeQuestion hawkeyeQuestion){
        //将选项修改为字符串
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonStr = objectMapper.writeValueAsString(hawkeyeQuestion.getOptionsList());
            hawkeyeQuestion.setOptions(jsonStr);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        //如果是多选题，设置答案内容
        if (hawkeyeQuestion.getQuestionType().equals("MULTI") || hawkeyeQuestion.getQuestionType().equals("MULTIPLE") ||
                (hawkeyeQuestion.getQuestionTypeChildren() != null &&
                        (hawkeyeQuestion.getQuestionTypeChildren().equals("MULTI")
                                || hawkeyeQuestion.getQuestionTypeChildren().equals("MULTIPLE")))) {

            String correctAnswer = "";
            for (int i = 0; i < hawkeyeQuestion.getCorrectAnswerList().size(); i++) {
                if (hawkeyeQuestion.getCorrectAnswerList().get(i)) {
                    correctAnswer += (char) ('A' + i) + ",";
                }
            }
            if (!correctAnswer.equals("")) {
                correctAnswer = correctAnswer.substring(0, correctAnswer.length() - 1);
            }
            hawkeyeQuestion.setCorrectAnswer(correctAnswer);
        } else if (hawkeyeQuestion.getQuestionType().equals("GAPFILL")
                || (hawkeyeQuestion.getQuestionTypeChildren() != null
                && hawkeyeQuestion.getQuestionTypeChildren().equals("GAPFILL"))) {
            if (hawkeyeQuestion.getGapFillAnswerList() != null) {
                try {
                    String jsonStr = objectMapper.writeValueAsString(hawkeyeQuestion.getGapFillAnswerList());
                    hawkeyeQuestion.setCorrectAnswer(jsonStr);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return hawkeyeQuestion;
    }

    private HawkeyeQuestion getQuestionDetails(HawkeyeQuestion hawkeyeQuestion) {
        HawkeyeKnowledgePoint knowledgePoint = new HawkeyeKnowledgePoint();
        knowledgePoint.setQuestionId(hawkeyeQuestion.getQuestionId());
        List<HawkeyeKnowledgePoint> knowledgePoints = hawkeyeKnowledgePointMapper.selectHawkeyeKnowledgePointList(knowledgePoint);
        List<Long> kpIds = new ArrayList<>();
        for (HawkeyeKnowledgePoint point : knowledgePoints) {
            kpIds.add(point.getKpId());
        }
        hawkeyeQuestion.setKpIds(kpIds);
        if (hawkeyeQuestion.getOptions() != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                hawkeyeQuestion.setOptionsList(mapper.readValue(hawkeyeQuestion.getOptions(), new TypeReference<>() {}));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        if (hawkeyeQuestion.getQuestionType().equals("MULTI") || hawkeyeQuestion.getQuestionType().equals("MULTIPLE") ||
                (hawkeyeQuestion.getQuestionTypeChildren() != null &&
                        (hawkeyeQuestion.getQuestionTypeChildren().equals("MULTI")
                                || hawkeyeQuestion.getQuestionTypeChildren().equals("MULTIPLE")))) {
            if (hawkeyeQuestion.getCorrectAnswer() != null && hawkeyeQuestion.getCorrectAnswer() != "") {
                List<Boolean> list = new ArrayList<>(Collections.nCopies(hawkeyeQuestion.getOptionsList().size(), false));
                String[] parts = hawkeyeQuestion.getCorrectAnswer().split(",");
                for (String part : parts) {
                    list.set(part.trim().charAt(0) - 'A', true);
                }
                hawkeyeQuestion.setCorrectAnswerList(list);
            }
        } else if (hawkeyeQuestion.getQuestionType().equals("GAPFILL")
                || (hawkeyeQuestion.getQuestionTypeChildren() != null
                && hawkeyeQuestion.getQuestionTypeChildren().equals("GAPFILL"))) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                hawkeyeQuestion.setGapFillAnswerList(mapper.readValue(hawkeyeQuestion.getCorrectAnswer(), new TypeReference<>() {}));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return hawkeyeQuestion;
    }
}
