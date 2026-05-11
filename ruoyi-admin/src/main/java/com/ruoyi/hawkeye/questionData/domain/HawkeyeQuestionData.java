package com.ruoyi.hawkeye.questionData.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 题目材料对象 hawkeye_question_data
 * 
 * @author ruoyi
 * @date 2026-04-28
 */
public class HawkeyeQuestionData extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long qdId;

    /** 所属分类 */
    @Excel(name = "所属分类")
    private Long categoryId;

    private String categoryName;

    /** 材料内容 */
    @Excel(name = "材料内容")
    private String qdContent;

    private String qbContentText;

    public void setQdId(Long qdId) 
    {
        this.qdId = qdId;
    }

    public Long getQdId() 
    {
        return qdId;
    }

    public void setCategoryId(Long categoryId) 
    {
        this.categoryId = categoryId;
    }

    public Long getCategoryId() 
    {
        return categoryId;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setQdContent(String qdContent)
    {
        this.qdContent = qdContent;
    }

    public String getQdContent() 
    {
        return qdContent;
    }

    public void setQbContentText(String qbContentText) {
        this.qbContentText = qbContentText;
    }

    public String getQbContentText() {
        return qbContentText;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("qdId", getQdId())
            .append("categoryId", getCategoryId())
            .append("qdContent", getQdContent())
            .toString();
    }
}
