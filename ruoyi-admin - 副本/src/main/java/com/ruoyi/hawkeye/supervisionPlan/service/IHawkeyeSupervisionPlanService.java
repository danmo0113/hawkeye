package com.ruoyi.hawkeye.supervisionPlan.service;

import java.util.List;
import com.ruoyi.hawkeye.supervisionPlan.domain.HawkeyeSupervisionPlan;

/**
 * 督学计划Service接口
 * 
 * @author 墨
 * @date 2026-04-28
 */
public interface IHawkeyeSupervisionPlanService 
{
    /**
     * 查询督学计划
     * 
     * @param planId 督学计划主键
     * @return 督学计划
     */
    public HawkeyeSupervisionPlan selectHawkeyeSupervisionPlanByPlanId(Long planId);

    /**
     * 查询督学计划列表
     * 
     * @param hawkeyeSupervisionPlan 督学计划
     * @return 督学计划集合
     */
    public List<HawkeyeSupervisionPlan> selectHawkeyeSupervisionPlanList(HawkeyeSupervisionPlan hawkeyeSupervisionPlan);

    /**
     * 新增督学计划
     * 
     * @param hawkeyeSupervisionPlan 督学计划
     * @return 结果
     */
    public int insertHawkeyeSupervisionPlan(HawkeyeSupervisionPlan hawkeyeSupervisionPlan);

    /**
     * 修改督学计划
     * 
     * @param hawkeyeSupervisionPlan 督学计划
     * @return 结果
     */
    public int updateHawkeyeSupervisionPlan(HawkeyeSupervisionPlan hawkeyeSupervisionPlan);

    /**
     * 批量删除督学计划
     * 
     * @param planIds 需要删除的督学计划主键集合
     * @return 结果
     */
    public int deleteHawkeyeSupervisionPlanByPlanIds(Long[] planIds);

    /**
     * 删除督学计划信息
     *
     * @param planId 督学计划主键
     * @return 结果
     */
    public int deleteHawkeyeSupervisionPlanByPlanId(Long planId);

    /**
     * 变更督学计划状态
     *
     * @param hawkeyeSupervisionPlan 督学计划
     * @return 结果
     */
    public int changeStatus(HawkeyeSupervisionPlan hawkeyeSupervisionPlan);

    /**
     * 复制督学计划
     *
     * @param planId 督学计划主键
     * @return 结果
     */
    public int copyPlan(Long planId);
}
