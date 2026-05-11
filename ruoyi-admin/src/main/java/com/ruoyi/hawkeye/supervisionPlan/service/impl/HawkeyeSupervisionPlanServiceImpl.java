package com.ruoyi.hawkeye.supervisionPlan.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.hawkeye.supervisionPlan.mapper.HawkeyeSupervisionPlanMapper;
import com.ruoyi.hawkeye.supervisionPlan.domain.HawkeyeSupervisionPlan;
import com.ruoyi.hawkeye.supervisionPlan.service.IHawkeyeSupervisionPlanService;

/**
 * 督学计划Service业务层处理
 * 
 * @author 墨
 * @date 2026-05-11
 */
@Service
public class HawkeyeSupervisionPlanServiceImpl implements IHawkeyeSupervisionPlanService 
{
    @Autowired
    private HawkeyeSupervisionPlanMapper hawkeyeSupervisionPlanMapper;

    /**
     * 查询督学计划
     * 
     * @param planId 督学计划主键
     * @return 督学计划
     */
    @Override
    public HawkeyeSupervisionPlan selectHawkeyeSupervisionPlanByPlanId(Long planId)
    {
        return hawkeyeSupervisionPlanMapper.selectHawkeyeSupervisionPlanByPlanId(planId);
    }

    /**
     * 查询督学计划列表
     * 
     * @param hawkeyeSupervisionPlan 督学计划
     * @return 督学计划
     */
    @Override
    public List<HawkeyeSupervisionPlan> selectHawkeyeSupervisionPlanList(HawkeyeSupervisionPlan hawkeyeSupervisionPlan)
    {
        return hawkeyeSupervisionPlanMapper.selectHawkeyeSupervisionPlanList(hawkeyeSupervisionPlan);
    }

    /**
     * 新增督学计划
     * 
     * @param hawkeyeSupervisionPlan 督学计划
     * @return 结果
     */
    @Override
    public int insertHawkeyeSupervisionPlan(HawkeyeSupervisionPlan hawkeyeSupervisionPlan)
    {
        hawkeyeSupervisionPlan.setCreateTime(DateUtils.getNowDate());
        return hawkeyeSupervisionPlanMapper.insertHawkeyeSupervisionPlan(hawkeyeSupervisionPlan);
    }

    /**
     * 修改督学计划
     * 
     * @param hawkeyeSupervisionPlan 督学计划
     * @return 结果
     */
    @Override
    public int updateHawkeyeSupervisionPlan(HawkeyeSupervisionPlan hawkeyeSupervisionPlan)
    {
        hawkeyeSupervisionPlan.setUpdateTime(DateUtils.getNowDate());
        return hawkeyeSupervisionPlanMapper.updateHawkeyeSupervisionPlan(hawkeyeSupervisionPlan);
    }

    /**
     * 批量删除督学计划
     * 
     * @param planIds 需要删除的督学计划主键
     * @return 结果
     */
    @Override
    public int deleteHawkeyeSupervisionPlanByPlanIds(Long[] planIds)
    {
        return hawkeyeSupervisionPlanMapper.deleteHawkeyeSupervisionPlanByPlanIds(planIds);
    }

    /**
     * 删除督学计划信息
     * 
     * @param planId 督学计划主键
     * @return 结果
     */
    @Override
    public int deleteHawkeyeSupervisionPlanByPlanId(Long planId)
    {
        return hawkeyeSupervisionPlanMapper.deleteHawkeyeSupervisionPlanByPlanId(planId);
    }
}
