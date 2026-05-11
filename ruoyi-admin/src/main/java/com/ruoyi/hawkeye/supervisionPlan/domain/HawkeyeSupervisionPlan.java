package com.ruoyi.hawkeye.supervisionPlan.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 督学计划对象 hawkeye_supervision_plan
 * 
 * @author 墨
 * @date 2026-05-11
 */
public class HawkeyeSupervisionPlan extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 计划ID */
    private Long planId;

    /** 所属分类 */
    @Excel(name = "所属分类")
    private Long categoryId;

    private String categoryName;

    /** 计划名称 */
    @Excel(name = "计划名称")
    private String planName;

    /** 计划类型（BASIC基础 STAGE冲刺 SPRINT押题） */
    @Excel(name = "计划类型", readConverterExp = "B=ASIC基础,S=TAGE冲刺,S=PRINT押题")
    private String planType;

    /** 总天数 */
    @Excel(name = "总天数")
    private Integer totalDays;

    /** 计划说明 */
    @Excel(name = "计划说明")
    private String description;

    /** 难度等级（1简单 2中等 3困难） */
    @Excel(name = "难度等级", readConverterExp = "1=简单,2=中等,3=困难")
    private Integer difficultyLevel;

    /** 状态 */
    private String status;

    /** 删除标志 */
    private String delFlag;

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setPlanId(Long planId)
    {
        this.planId = planId;
    }

    public Long getPlanId() 
    {
        return planId;
    }

    public void setCategoryId(Long categoryId) 
    {
        this.categoryId = categoryId;
    }

    public Long getCategoryId() 
    {
        return categoryId;
    }

    public void setPlanName(String planName) 
    {
        this.planName = planName;
    }

    public String getPlanName() 
    {
        return planName;
    }

    public void setPlanType(String planType) 
    {
        this.planType = planType;
    }

    public String getPlanType() 
    {
        return planType;
    }

    public void setTotalDays(Integer totalDays) 
    {
        this.totalDays = totalDays;
    }

    public Integer getTotalDays() 
    {
        return totalDays;
    }

    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }

    public void setDifficultyLevel(Integer difficultyLevel) 
    {
        this.difficultyLevel = difficultyLevel;
    }

    public Integer getDifficultyLevel() 
    {
        return difficultyLevel;
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
            .append("planId", getPlanId())
            .append("categoryId", getCategoryId())
            .append("planName", getPlanName())
            .append("planType", getPlanType())
            .append("totalDays", getTotalDays())
            .append("description", getDescription())
            .append("difficultyLevel", getDifficultyLevel())
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
