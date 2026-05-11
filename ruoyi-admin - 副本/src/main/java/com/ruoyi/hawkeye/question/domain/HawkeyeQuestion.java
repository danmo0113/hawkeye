package com.ruoyi.hawkeye.question.domain;

import com.ruoyi.hawkeye.knowledgePoint.domain.HawkeyeKnowledgePoint;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;
import java.util.Map;

/**
 * 题库题目对象 hawkeye_question
 * 
 * @author 墨
 * @date 2026-04-27
 */
public class HawkeyeQuestion extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 题目ID */
    private Long questionId;

    /** 题干内容（可含图片） */
    @Excel(name = "题目", readConverterExp = "可含图片")
    private String questionContent;

    /** 题型 */
    @Excel(name = "题型")
    private String questionType;

    /** 所属三级分类ID（科目） */
//    @Excel(name = "所属分类", readConverterExp = "科=目")
    private String categoryName;

    private List<Long> kpIds;

    private String kpNames;



    private String questionTypeChildren;

    /**
     * 选项内容（JSON格式，如[{"label":"A","content":"..."}]）
     */
//    @Excel(name = "选项内容", readConverterExp = "J=SON格式")
    private String options;

//    @Excel(name = "选项内容", readConverterExp = "J=SON格式")
    private List<Map<String, String>> optionsList;

    /** 正确答案（单选A/多选A,B/判断1,0/材料题存子题答案组合） */
//    @Excel(name = "正确答案", readConverterExp = "单=选A/多选A,B/判断1,0/材料题存子题答案组合")
    private String correctAnswer;

//    @Excel(name = "正确答案", readConverterExp = "单=选A/多选A,B/判断1,0/材料题存子题答案组合")
    private List<Boolean> correctAnswerList;

    /** 答案解析 */
//    @Excel(name = "答案解析")
    private String analysis;

    /** 难度 */
    private Integer difficulty;

    /** 历年考频次数（展示于右上角） */
    private Integer examFrequency;

    /** 所属材料题ID（如果是材料题的子题，则指向父材料题） */
//    @Excel(name = "所属材料题ID", readConverterExp = "如=果是材料题的子题，则指向父材料题")
    private Long materialId;

    /** 材料内容（仅当question_type=MATERIAL时有效） */
//    @Excel(name = "材料内容", readConverterExp = "仅=当question_type=MATERIAL时有效")
    private String materialContent;

    /** 状态（0正常 1停用） */
    private String status;

    /** 删除标志（0存在 2删除） */
    private String delFlag;

    private List<Map<String,String>> gapFillAnswerList;

    private List<HawkeyeKnowledgePoint> knowledgePointList;

    public void setKpNames(String kpNames) {
        this.kpNames = kpNames;
    }

    public String getKpNames() {
        return kpNames;
    }

    public void setKnowledgePointList(List<HawkeyeKnowledgePoint> knowledgePointList) {
        knowledgePointList = knowledgePointList;
    }

    public List<HawkeyeKnowledgePoint> getKnowledgePointList() {
        return knowledgePointList;
    }

    public void setQuestionTypeChildren(String questionTypeChildren) {
        this.questionTypeChildren = questionTypeChildren;
    }

    public String getQuestionTypeChildren() {
        return questionTypeChildren;
    }

    public void setGapFillAnswerList(List<Map<String,String>> gapFillAnswerList) {
        this.gapFillAnswerList = gapFillAnswerList;
    }

    public List<Map<String,String>> getGapFillAnswerList() {
        return gapFillAnswerList;
    }

    public void setQuestionId(Long questionId) 
    {
        this.questionId = questionId;
    }

    public Long getQuestionId() 
    {
        return questionId;
    }

    public List<Long> getKpIds() { return kpIds; }

    public void setKpIds(List<Long> kpIds) { this.kpIds=kpIds; }

    public void setQuestionType(String questionType) 
    {
        this.questionType = questionType;
    }

    public String getQuestionType() 
    {
        return questionType;
    }

    public void setQuestionContent(String questionContent) 
    {
        this.questionContent = questionContent;
    }

    public String getQuestionContent() 
    {
        return questionContent;
    }

    public void setOptions(String options)
    {
        this.options = options;
    }

    public String getOptions()
    {
        return options;
    }

    public void setOptionsList(List<Map<String, String>> optionsList) {
        this.optionsList = optionsList;
    }

    public List<Map<String, String>> getOptionsList() {
        return optionsList;
    }

    public void setCorrectAnswer(String correctAnswer)
    {
        this.correctAnswer = correctAnswer;
    }

    public String getCorrectAnswer() 
    {
        return correctAnswer;
    }

    public void setAnalysis(String analysis) 
    {
        this.analysis = analysis;
    }

    public String getAnalysis() 
    {
        return analysis;
    }

    public void setCorrectAnswerList(List<Boolean> correctAnswerList) {
        this.correctAnswerList = correctAnswerList;
    }

    public List<Boolean> getCorrectAnswerList() {
        return correctAnswerList;
    }

    public void setDifficulty(Integer difficulty)
    {
        this.difficulty = difficulty;
    }

    public Integer getDifficulty() 
    {
        return difficulty;
    }

    public void setExamFrequency(Integer examFrequency) 
    {
        this.examFrequency = examFrequency;
    }

    public Integer getExamFrequency() 
    {
        return examFrequency;
    }

    public void setMaterialId(Long materialId) 
    {
        this.materialId = materialId;
    }

    public Long getMaterialId() 
    {
        return materialId;
    }

    public void setMaterialContent(String materialContent) 
    {
        this.materialContent = materialContent;
    }

    public String getMaterialContent() 
    {
        return materialContent;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("questionId", getQuestionId())
            .append("questionType", getQuestionType())
            .append("questionContent", getQuestionContent())
            .append("options", getOptions())
            .append("correctAnswer", getCorrectAnswer())
            .append("analysis", getAnalysis())
            .append("difficulty", getDifficulty())
            .append("examFrequency", getExamFrequency())
            .append("materialId", getMaterialId())
            .append("materialContent", getMaterialContent())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
