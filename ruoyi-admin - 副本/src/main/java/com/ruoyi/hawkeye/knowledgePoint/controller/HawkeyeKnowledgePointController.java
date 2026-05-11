package com.ruoyi.hawkeye.knowledgePoint.controller;

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
import com.ruoyi.hawkeye.knowledgePoint.domain.HawkeyeKnowledgePoint;
import com.ruoyi.hawkeye.knowledgePoint.service.IHawkeyeKnowledgePointService;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 章节管理Controller
 * 
 * @author 墨
 * @date 2026-04-22
 */
@RestController
@RequestMapping("/hawkeye/knowledgePoint")
public class HawkeyeKnowledgePointController extends BaseController
{
    @Autowired
    private IHawkeyeKnowledgePointService hawkeyeKnowledgePointService;

    /**
     * 查询章节管理列表
     */
    @PreAuthorize("@ss.hasPermi('hawkeye:knowledgePoint:list')")
    @GetMapping("/list")
    public AjaxResult list(HawkeyeKnowledgePoint hawkeyeKnowledgePoint)
    {
        List<HawkeyeKnowledgePoint> list = hawkeyeKnowledgePointService.selectHawkeyeKnowledgePointList(hawkeyeKnowledgePoint);
        return success(list);
    }

    /**
     * 导出章节管理列表
     */
    @PreAuthorize("@ss.hasPermi('hawkeye:knowledgePoint:export')")
    @Log(title = "章节管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HawkeyeKnowledgePoint hawkeyeKnowledgePoint)
    {
        List<HawkeyeKnowledgePoint> list = hawkeyeKnowledgePointService.selectHawkeyeKnowledgePointList(hawkeyeKnowledgePoint);
        ExcelUtil<HawkeyeKnowledgePoint> util = new ExcelUtil<HawkeyeKnowledgePoint>(HawkeyeKnowledgePoint.class);
        util.exportExcel(response, list, "章节管理数据");
    }

    /**
     * 获取章节管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('hawkeye:knowledgePoint:query')")
    @GetMapping(value = "/{kpId}")
    public AjaxResult getInfo(@PathVariable("kpId") Long kpId)
    {
        return success(hawkeyeKnowledgePointService.selectHawkeyeKnowledgePointByKpId(kpId));
    }

    /**
     * 新增章节管理
     */
    @PreAuthorize("@ss.hasPermi('hawkeye:knowledgePoint:add')")
    @Log(title = "章节管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HawkeyeKnowledgePoint hawkeyeKnowledgePoint)
    {
        return toAjax(hawkeyeKnowledgePointService.insertHawkeyeKnowledgePoint(hawkeyeKnowledgePoint));
    }

    /**
     * 修改章节管理
     */
    @PreAuthorize("@ss.hasPermi('hawkeye:knowledgePoint:edit')")
    @Log(title = "章节管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HawkeyeKnowledgePoint hawkeyeKnowledgePoint)
    {
        return toAjax(hawkeyeKnowledgePointService.updateHawkeyeKnowledgePoint(hawkeyeKnowledgePoint));
    }

    /**
     * 删除章节管理
     */
    @PreAuthorize("@ss.hasPermi('hawkeye:knowledgePoint:remove')")
    @Log(title = "章节管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{kpIds}")
    public AjaxResult remove(@PathVariable Long[] kpIds)
    {
        return toAjax(hawkeyeKnowledgePointService.deleteHawkeyeKnowledgePointByKpIds(kpIds));
    }
}
