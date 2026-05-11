package com.ruoyi.hawkeye.questionKp.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 题目-考点关联对象 hawkeye_question_kp
 * 
 * @author ruoyi
 * @date 2026-04-28
 */
public class HawkeyeQuestionKp extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 题目ID */
    @Excel(name = "题目ID")
    private Long questionId;

    /** 考点ID */
    @Excel(name = "考点ID")
    private Long kpId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setQuestionId(Long questionId) 
    {
        this.questionId = questionId;
    }

    public Long getQuestionId() 
    {
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("questionId", getQuestionId())
            .append("kpId", getKpId())
            .toString();
    }
}
