package com.ruoyi.hawkeye.category.controller;

import java.util.List;
import java.util.Map;

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
import com.ruoyi.hawkeye.category.domain.HawkeyeExamCategory;
import com.ruoyi.hawkeye.category.service.IHawkeyeExamCategoryService;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 考试分类Controller
 * 
 * @author 墨
 * @date 2026-04-20
 */
@RestController
@RequestMapping("/hawkeye/category")
public class HawkeyeExamCategoryController extends BaseController
{
    @Autowired
    private IHawkeyeExamCategoryService hawkeyeExamCategoryService;

    /**
     * 查询考试分类列表
     */
    @PreAuthorize("@ss.hasPermi('hawkeye:category:list')")
    @GetMapping("/list")
    public AjaxResult list(HawkeyeExamCategory hawkeyeExamCategory)
    {
        List<HawkeyeExamCategory> list = hawkeyeExamCategoryService.selectHawkeyeExamCategoryList(hawkeyeExamCategory);
        return success(list);
    }

    /**
     * 导出考试分类列表
     */
    @PreAuthorize("@ss.hasPermi('hawkeye:category:export')")
    @Log(title = "考试分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HawkeyeExamCategory hawkeyeExamCategory)
    {
        List<HawkeyeExamCategory> list = hawkeyeExamCategoryService.selectHawkeyeExamCategoryList(hawkeyeExamCategory);
        ExcelUtil<HawkeyeExamCategory> util = new ExcelUtil<HawkeyeExamCategory>(HawkeyeExamCategory.class);
        util.exportExcel(response, list, "考试分类数据");
    }

    /**
     * 获取考试分类详细信息
     */
    @PreAuthorize("@ss.hasPermi('hawkeye:category:query')")
    @GetMapping(value = "/{categoryId}")
    public AjaxResult getInfo(@PathVariable("categoryId") Long categoryId)
    {
        return success(hawkeyeExamCategoryService.selectHawkeyeExamCategoryByCategoryId(categoryId));
    }

    /**
     * 新增考试分类
     */
    @PreAuthorize("@ss.hasPermi('hawkeye:category:add')")
    @Log(title = "考试分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HawkeyeExamCategory hawkeyeExamCategory)
    {
        return toAjax(hawkeyeExamCategoryService.insertHawkeyeExamCategory(hawkeyeExamCategory));
    }

    /**
     * 修改考试分类
     */
    @PreAuthorize("@ss.hasPermi('hawkeye:category:edit')")
    @Log(title = "考试分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HawkeyeExamCategory hawkeyeExamCategory)
    {
        return toAjax(hawkeyeExamCategoryService.updateHawkeyeExamCategory(hawkeyeExamCategory));
    }

    /**
     * 保存菜单排序
     */
    @PreAuthorize("@ss.hasPermi('hawkeye:category:edit')")
    @Log(title = "保存菜单排序", businessType = BusinessType.UPDATE)
    @PutMapping("/updateSort")
    public AjaxResult updateSort(@RequestBody Map<String, String> params)
    {
        String[] categoryIds = params.get("categoryIds").split(",");
        String[] orderNums = params.get("orderNums").split(",");
        hawkeyeExamCategoryService.updateHawkeyeExamCategorySort(categoryIds, orderNums);
        return success();
    }

    /**
     * 删除考试分类
     */
    @PreAuthorize("@ss.hasPermi('hawkeye:category:remove')")
    @Log(title = "考试分类", businessType = BusinessType.DELETE)
	@DeleteMapping("/{categoryIds}")
    public AjaxResult remove(@PathVariable Long[] categoryIds)
    {
        return toAjax(hawkeyeExamCategoryService.deleteHawkeyeExamCategoryByCategoryIds(categoryIds));
    }
}
