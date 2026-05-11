package com.ruoyi.hawkeye.questionData.controller;

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
import com.ruoyi.hawkeye.questionData.domain.HawkeyeQuestionData;
import com.ruoyi.hawkeye.questionData.service.IHawkeyeQuestionDataService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 题目材料Controller
 * 
 * @author ruoyi
 * @date 2026-04-28
 */
@RestController
@RequestMapping("/hawkeye/questionData")
public class HawkeyeQuestionDataController extends BaseController
{
    @Autowired
    private IHawkeyeQuestionDataService hawkeyeQuestionDataService;

    /**
     * 查询题目材料列表
     */
    @PreAuthorize("@ss.hasPermi('hawkeye:questionData:list')")
    @GetMapping("/list")
    public TableDataInfo list(HawkeyeQuestionData hawkeyeQuestionData)
    {
        startPage();
        List<HawkeyeQuestionData> list = hawkeyeQuestionDataService.selectHawkeyeQuestionDataList(hawkeyeQuestionData);
        return getDataTable(list);
    }

    /**
     * 导出题目材料列表
     */
    @PreAuthorize("@ss.hasPermi('hawkeye:questionData:export')")
    @Log(title = "题目材料", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HawkeyeQuestionData hawkeyeQuestionData)
    {
        List<HawkeyeQuestionData> list = hawkeyeQuestionDataService.selectHawkeyeQuestionDataList(hawkeyeQuestionData);
        ExcelUtil<HawkeyeQuestionData> util = new ExcelUtil<HawkeyeQuestionData>(HawkeyeQuestionData.class);
        util.exportExcel(response, list, "题目材料数据");
    }

    /**
     * 获取题目材料详细信息
     */
    @PreAuthorize("@ss.hasPermi('hawkeye:questionData:query')")
    @GetMapping(value = "/{qdId}")
    public AjaxResult getInfo(@PathVariable("qdId") Long qdId)
    {
        return success(hawkeyeQuestionDataService.selectHawkeyeQuestionDataByQdId(qdId));
    }

    /**
     * 新增题目材料
     */
    @PreAuthorize("@ss.hasPermi('hawkeye:questionData:add')")
    @Log(title = "题目材料", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HawkeyeQuestionData hawkeyeQuestionData)
    {
        return toAjax(hawkeyeQuestionDataService.insertHawkeyeQuestionData(hawkeyeQuestionData));
    }

    /**
     * 修改题目材料
     */
    @PreAuthorize("@ss.hasPermi('hawkeye:questionData:edit')")
    @Log(title = "题目材料", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HawkeyeQuestionData hawkeyeQuestionData)
    {
        return toAjax(hawkeyeQuestionDataService.updateHawkeyeQuestionData(hawkeyeQuestionData));
    }

    /**
     * 删除题目材料
     */
    @PreAuthorize("@ss.hasPermi('hawkeye:questionData:remove')")
    @Log(title = "题目材料", businessType = BusinessType.DELETE)
	@DeleteMapping("/{qdIds}")
    public AjaxResult remove(@PathVariable Long[] qdIds)
    {
        return toAjax(hawkeyeQuestionDataService.deleteHawkeyeQuestionDataByQdIds(qdIds));
    }
}
