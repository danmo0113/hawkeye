package com.ruoyi.hawkeye.supervisionPlan.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.hawkeye.supervisionPlan.domain.HawkeyeSupervisionPlan;
import com.ruoyi.hawkeye.supervisionPlan.service.IHawkeyeSupervisionPlanService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 督学计划Controller
 * 
 * @author 墨
 * @date 2026-05-11
 */
@RestController
@RequestMapping("/hawkeye/supervisionPlan")
public class HawkeyeSupervisionPlanController extends BaseController
{
    @Autowired
    private IHawkeyeSupervisionPlanService hawkeyeSupervisionPlanService;

    /**
     * 查询督学计划列表
     */
    @PreAuthorize("@ss.hasPermi('hawkeye:supervisionPlan:list')")
    @GetMapping("/list")
    public TableDataInfo list(HawkeyeSupervisionPlan hawkeyeSupervisionPlan)
    {
        startPage();
        List<HawkeyeSupervisionPlan> list = hawkeyeSupervisionPlanService.selectHawkeyeSupervisionPlanList(hawkeyeSupervisionPlan);
        return getDataTable(list);
    }

    /**
     * 导出督学计划列表
     */
    @PreAuthorize("@ss.hasPermi('hawkeye:supervisionPlan:export')")
    @Log(title = "督学计划", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HawkeyeSupervisionPlan hawkeyeSupervisionPlan)
    {
        List<HawkeyeSupervisionPlan> list = hawkeyeSupervisionPlanService.selectHawkeyeSupervisionPlanList(hawkeyeSupervisionPlan);
        ExcelUtil<HawkeyeSupervisionPlan> util = new ExcelUtil<HawkeyeSupervisionPlan>(HawkeyeSupervisionPlan.class);
        util.exportExcel(response, list, "督学计划数据");
    }

    /**
     * 获取督学计划详细信息
     */
    @PreAuthorize("@ss.hasPermi('hawkeye:supervisionPlan:query')")
    @GetMapping(value = "/{planId}")
    public AjaxResult getInfo(@PathVariable("planId") Long planId)
    {
        return success(hawkeyeSupervisionPlanService.selectHawkeyeSupervisionPlanByPlanId(planId));
    }

    /**
     * 新增督学计划
     */
    @PreAuthorize("@ss.hasPermi('hawkeye:supervisionPlan:add')")
    @Log(title = "督学计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HawkeyeSupervisionPlan hawkeyeSupervisionPlan)
    {
        return toAjax(hawkeyeSupervisionPlanService.insertHawkeyeSupervisionPlan(hawkeyeSupervisionPlan));
    }

    /**
     * 修改督学计划
     */
    @PreAuthorize("@ss.hasPermi('hawkeye:supervisionPlan:edit')")
    @Log(title = "督学计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HawkeyeSupervisionPlan hawkeyeSupervisionPlan)
    {
        return toAjax(hawkeyeSupervisionPlanService.updateHawkeyeSupervisionPlan(hawkeyeSupervisionPlan));
    }

    /**
     * 删除督学计划
     */
    @PreAuthorize("@ss.hasPermi('hawkeye:supervisionPlan:remove')")
    @Log(title = "督学计划", businessType = BusinessType.DELETE)
	@DeleteMapping("/{planIds}")
    public AjaxResult remove(@PathVariable Long[] planIds)
    {
        return toAjax(hawkeyeSupervisionPlanService.deleteHawkeyeSupervisionPlanByPlanIds(planIds));
    }
}
