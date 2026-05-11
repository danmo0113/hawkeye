package com.ruoyi.hawkeye.knowledgePoint.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.TreeEntity;

/**
 * 章节管理对象 hawkeye_knowledge_point
 * 
 * @author 墨
 * @date 2026-04-22
 */
public class HawkeyeKnowledgePoint extends TreeEntity
{
    private static final long serialVersionUID = 1L;

    /** 考点ID */
    private Long kpId;

    private Long questionId;

    /** 所属分类 */
    @Excel(name = "所属分类")
    private Long categoryId;

    @Excel(name = "所属分类")
    private String categoryName;

    /** 考点名称 */
    @Excel(name = "考点名称")
    private String kpName;

    private String kpNamePath;

    /** 层级（1章 2节 3小节 4知识点） */
    @Excel(name = "层级", readConverterExp = "1=章,2=节,3=小节,4=知识点")
    private Integer level;

    /** 重要性（H高频 M中频 L低频） */
    private String importance;

    /** 建议学习时长（分钟） */
    @Excel(name = "建议学习时长", readConverterExp = "分=钟")
    private Integer suggestedDuration;

    /** 排序 */
    @Excel(name = "排序")
    private Integer sortOrder;

    /** 状态（0正常 1停用） */
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setKpId(Long kpId)
    {
        this.kpId = kpId;
    }

    public Long getKpId() 
    {
        return kpId;
    }

    public void setCategoryId(Long categoryId) 
    {
        this.categoryId = categoryId;
    }
    public void setCategoryName(String categoryName)
    {
        this.categoryName = categoryName;
    }

    public Long getCategoryId()
    {
        return categoryId;
    }

    public String getCategoryName()
    {
        return categoryName;
    }

    public void setKpName(String kpName) 
    {
        this.kpName = kpName;
    }

    public String getKpName() 
    {
        return kpName;
    }

    public void setLevel(Integer level) 
    {
        this.level = level;
    }

    public Integer getLevel() 
    {
        return level;
    }

    public void setImportance(String importance) 
    {
        this.importance = importance;
    }

    public String getImportance() 
    {
        return importance;
    }

    public void setSuggestedDuration(Integer suggestedDuration) 
    {
        this.suggestedDuration = suggestedDuration;
    }

    public Integer getSuggestedDuration() 
    {
        return suggestedDuration;
    }

    public void setSortOrder(Integer sortOrder) 
    {
        this.sortOrder = sortOrder;
    }

    public Integer getSortOrder() 
    {
        return sortOrder;
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

    public void setKpNamePath(String kpNamePath) {
        this.kpNamePath = kpNamePath;
    }

    public String getKpNamePath() {
        return this.kpNamePath;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("kpId", getKpId())
            .append("categoryId", getCategoryId())
            .append("categoryName", getCategoryName())
            .append("parentId", getParentId())
            .append("kpName", getKpName())
            .append("level", getLevel())
            .append("importance", getImportance())
            .append("suggestedDuration", getSuggestedDuration())
            .append("sortOrder", getSortOrder())
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
