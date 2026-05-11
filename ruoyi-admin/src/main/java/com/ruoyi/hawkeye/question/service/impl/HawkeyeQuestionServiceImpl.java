package com.ruoyi.hawkeye.question.service.impl;

import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;

import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.stream.Collectors;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hawkeye.knowledgePoint.domain.HawkeyeKnowledgePoint;
import com.ruoyi.hawkeye.knowledgePoint.mapper.HawkeyeKnowledgePointMapper;
import com.ruoyi.hawkeye.question.domain.HawkeyeExportQuestion;
import com.ruoyi.hawkeye.questionKp.domain.HawkeyeQuestionKp;
import com.ruoyi.hawkeye.questionKp.mapper.HawkeyeQuestionKpMapper;
import jakarta.servlet.http.HttpServletResponse;
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
     * @param hawkeyeQuestionList 题库题目
     * @return 题库题目
     */
    @Override
    public void selectHawkeyeQuestionListExport(HttpServletResponse response, List<HawkeyeQuestion> hawkeyeQuestionList) {
        List<HawkeyeExportQuestion> questionList = new ArrayList<>();
        for (HawkeyeQuestion question : hawkeyeQuestionList) {
            HawkeyeExportQuestion exportQuestion = new HawkeyeExportQuestion();
            //题干内瓤
            exportQuestion.setQuestionContent(this.removeHtmlTags(question.getQuestionContent()));
            //题目类型
            exportQuestion.setQuestionType(question.getQuestionType());
            //选项内容
            if (question.getOptions() != null) {
                List<Map<String, String>> optionList = this.parseJsonArray(question.getOptions());
                for (Map<String, String> optionMap : optionList) {
                    if (optionMap.get("label").equals("A")) {
                        exportQuestion.setOptionA(this.removeHtmlTags(optionMap.get("content")));
                    } else if (optionMap.get("label").equals("B")) {
                        exportQuestion.setOptionB(this.removeHtmlTags(optionMap.get("content")));
                    } else if (optionMap.get("label").equals("C")) {
                        exportQuestion.setOptionC(this.removeHtmlTags(optionMap.get("content")));
                    } else if (optionMap.get("label").equals("D")) {
                        exportQuestion.setOptionD(this.removeHtmlTags(optionMap.get("content")));
                    }
                }

            }
            //答案
            if (question.getQuestionType().equals("JUDGE")) {
                if (question.getCorrectAnswer().equals("0")) {
                    exportQuestion.setCorrectAnswer("错");
                } else {
                    exportQuestion.setCorrectAnswer("对");
                }
            } else if (question.getQuestionType().equals("GAPFILL")) {
                List<Map<String, String>> answerList = this.parseJsonArray(question.getCorrectAnswer());
                String answerString = "";
                for (Map<String, String> answer : answerList) {
                    answerString += "答案" + answer.get("label") + ":" + answer.get("content") + ",";
                }
                if (!answerString.equals("")) {
                    exportQuestion.setCorrectAnswer(answerString.substring(0, answerString.length() - 1));
                }

            } else if (question.getQuestionType().equals("ESSAY")) {
                exportQuestion.setCorrectAnswer(this.removeHtmlTags(question.getCorrectAnswer()));
            } else {
                exportQuestion.setCorrectAnswer(question.getCorrectAnswer());
            }
            //答案解析
            exportQuestion.setAnalysis(this.removeHtmlTags(question.getAnalysis()));
            //分类
            String kpName1 = "";
            String kpName2 = "";
            String kpName3 = "";
            String kpName4 = "";
            for (HawkeyeKnowledgePoint point : question.getKnowledgePointList()) {
                if (point.getKpNamePath() != null && !point.getKpNamePath().equals("")) {
                    List<String> kpNameList = new ArrayList<>(Arrays.asList(point.getKpNamePath().split("->")));
                    for (int i = 0; i < kpNameList.size(); i++) {
                        if (i == 0) {
                            kpName1 += kpNameList.get(i) + ",";
                        } else if (i == 1) {
                            kpName2 += kpNameList.get(i) + ",";
                        } else if (i == 2) {
                            kpName3 += kpNameList.get(i) + ",";
                        } else if (i == 3) {
                            kpName4 += kpNameList.get(i) + ",";
                        }
                    }
                }
            }
            if (!kpName1.equals("")) {
                kpName1 = kpName1.substring(0, kpName1.length() - 1);
                kpName1 = Arrays.stream(kpName1.split(",")).distinct().collect(Collectors.joining(","));
                exportQuestion.setKpName1(kpName1);
            }
            if (!kpName2.equals("")) {
                kpName2 = kpName2.substring(0, kpName2.length() - 1);
                kpName2 = Arrays.stream(kpName2.split(",")).distinct().collect(Collectors.joining(","));
                exportQuestion.setKpName2(kpName2);
            }
            if (!kpName3.equals("")) {
                kpName3 = kpName3.substring(0, kpName3.length() - 1);
                kpName3 = Arrays.stream(kpName3.split(",")).distinct().collect(Collectors.joining(","));
                exportQuestion.setKpName3(kpName3);
            }
            if (!kpName4.equals("")) {
                kpName4 = kpName4.substring(0, kpName4.length() - 1);
                kpName4 = Arrays.stream(kpName4.split(",")).distinct().collect(Collectors.joining(","));
                exportQuestion.setKpName4(kpName4);
            }
            questionList.add(exportQuestion);
        }
        ExcelUtil<HawkeyeExportQuestion> util = new ExcelUtil<HawkeyeExportQuestion>(HawkeyeExportQuestion.class);
        util.exportExcel(response, questionList, "题库题目数据");
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

    @Override
    public List<HawkeyeQuestion> selectHawkeyeQuestionPathList(HawkeyeQuestion hawkeyeQuestion) {
        return hawkeyeQuestionMapper.selectHawkeyeQuestionPathList(hawkeyeQuestion);
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

    /**
     * 富文本带标签的文本去除标签
     * @param html
     * @return
     */
    private String removeHtmlTags(String html) {
        if (html == null || html.isEmpty()) {
            return "";
        }
        // 解析 HTML 并获取纯文本
        Document document = Jsoup.parse(html);
        return document.text();  // 自动去除标签、合并空白，返回纯文本
    }

    /**
     * 字符串转换为List<Map<String, String>>
     * @param jsonArrayStr
     * @return
     */
    private List<Map<String, String>> parseJsonArray(String jsonArrayStr) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // 注意 TypeReference 指定了 Map 的 key 和 value 都是 String 类型
            return mapper.readValue(jsonArrayStr, new TypeReference<List<Map<String, String>>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON array", e);
        }
    }
}
