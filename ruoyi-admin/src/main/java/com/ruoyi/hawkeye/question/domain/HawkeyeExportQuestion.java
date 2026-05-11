package com.ruoyi.hawkeye.question.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.hawkeye.knowledgePoint.domain.HawkeyeKnowledgePoint;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;
import java.util.Map;

/**
 * 题库题目对象 hawkeye_question
 * 
 * @author 墨
 * @date 2026-04-27
 */
public class HawkeyeExportQuestion extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 题干内容（可含图片） */
    @Excel(name = "题目")
    private String questionContent;

    /** 题型 */
    @Excel(name = "题型", readConverterExp = "SINGLE=单选题,MULTI=多选题,MULTIPLE=不定项选择题,JUDGE=判断题,GAPFILL=填空题,ESSAY=问答题,MATERIAL=材料题")
    private String questionType;

    @Excel(name = "选项A")
    private String optionA;

    @Excel(name = "选项B")
    private String optionB;

    @Excel(name = "选项C")
    private String optionC;


    @Excel(name = "选项D")
    private String optionD;

    @Excel(name = "答案")
    private String correctAnswer;


    @Excel(name = "解析")
    private String analysis;

    @Excel(name = "一级分类")
    private String kpName1;

    @Excel(name = "二级分类")
    private String kpName2;

    @Excel(name = "三级分类")
    private String kpName3;

    @Excel(name = "四级分类")
    private String kpName4;

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public String getKpName1() {
        return kpName1;
    }

    public void setKpName1(String kpName1) {
        this.kpName1 = kpName1;
    }

    public String getKpName2() {
        return kpName2;
    }

    public void setKpName2(String kpName2) {
        this.kpName2 = kpName2;
    }

    public String getKpName3() {
        return kpName3;
    }

    public void setKpName3(String kpName3) {
        this.kpName3 = kpName3;
    }

    public String getKpName4() {
        return kpName4;
    }

    public void setKpName4(String kpName4) {
        this.kpName4 = kpName4;
    }

    @Override
    public String toString() {
        return "HawkeyeExportQuestion{" +
                "questionContent='" + questionContent + '\'' +
                ", questionType='" + questionType + '\'' +
                ", optionA='" + optionA + '\'' +
                ", optionB='" + optionB + '\'' +
                ", optionC='" + optionC + '\'' +
                ", optionD='" + optionD + '\'' +
                ", correctAnswer='" + correctAnswer + '\'' +
                ", analysis='" + analysis + '\'' +
                ", kpName1='" + kpName1 + '\'' +
                ", kpName2='" + kpName2 + '\'' +
                ", kpName3='" + kpName3 + '\'' +
                ", kpName4='" + kpName4 + '\'' +
                '}';
    }
}
