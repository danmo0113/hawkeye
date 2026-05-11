package com.ruoyi.hawkeye.category.service.impl;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysMenu;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.hawkeye.category.mapper.HawkeyeExamCategoryMapper;
import com.ruoyi.hawkeye.category.domain.HawkeyeExamCategory;
import com.ruoyi.hawkeye.category.service.IHawkeyeExamCategoryService;

/**
 * 考试分类Service业务层处理
 * 
 * @author 墨
 * @date 2026-04-20
 */
@Service
public class HawkeyeExamCategoryServiceImpl implements IHawkeyeExamCategoryService 
{
    @Autowired
    private HawkeyeExamCategoryMapper hawkeyeExamCategoryMapper;

    /**
     * 查询考试分类
     * 
     * @param categoryId 考试分类主键
     * @return 考试分类
     */
    @Override
    public HawkeyeExamCategory selectHawkeyeExamCategoryByCategoryId(Long categoryId)
    {
        return hawkeyeExamCategoryMapper.selectHawkeyeExamCategoryByCategoryId(categoryId);
    }

    /**
     * 查询考试分类列表
     * 
     * @param hawkeyeExamCategory 考试分类
     * @return 考试分类
     */
    @Override
    public List<HawkeyeExamCategory> selectHawkeyeExamCategoryList(HawkeyeExamCategory hawkeyeExamCategory)
    {
        return hawkeyeExamCategoryMapper.selectHawkeyeExamCategoryList(hawkeyeExamCategory);
    }

    /**
     * 新增考试分类
     * 
     * @param hawkeyeExamCategory 考试分类
     * @return 结果
     */
    @Override
    public int insertHawkeyeExamCategory(HawkeyeExamCategory hawkeyeExamCategory)
    {
        hawkeyeExamCategory.setCreateTime(DateUtils.getNowDate());
        if (hawkeyeExamCategory.getParentId() != null) {
            HawkeyeExamCategory category = hawkeyeExamCategoryMapper.selectHawkeyeExamCategoryByCategoryId(hawkeyeExamCategory.getParentId());
            hawkeyeExamCategory.setLevel(category.getLevel() + 1);
        }
        return hawkeyeExamCategoryMapper.insertHawkeyeExamCategory(hawkeyeExamCategory);
    }

    /**
     * 修改考试分类
     * 
     * @param hawkeyeExamCategory 考试分类
     * @return 结果
     */
    @Override
    public int updateHawkeyeExamCategory(HawkeyeExamCategory hawkeyeExamCategory)
    {
        hawkeyeExamCategory.setUpdateTime(DateUtils.getNowDate());
        return hawkeyeExamCategoryMapper.updateHawkeyeExamCategory(hawkeyeExamCategory);
    }

    @Override
    public void updateHawkeyeExamCategorySort(String[] categoryIds, String[] orderNums) {
        try
        {
            for (int i = 0; i < categoryIds.length; i++)
            {
                HawkeyeExamCategory category = new HawkeyeExamCategory();
                category.setCategoryId(Convert.toLong(categoryIds[i]));
                category.setSortOrder(Convert.toInt(orderNums[i]));
                hawkeyeExamCategoryMapper.updateHawkeyeExamCategory(category);
            }
        }
        catch (Exception e)
        {
            throw new ServiceException("保存排序异常，请联系管理员");
        }
    }

    /**
     * 批量删除考试分类
     * 
     * @param categoryIds 需要删除的考试分类主键
     * @return 结果
     */
    @Override
    public int deleteHawkeyeExamCategoryByCategoryIds(Long[] categoryIds)
    {
        return hawkeyeExamCategoryMapper.deleteHawkeyeExamCategoryByCategoryIds(categoryIds);
    }

    /**
     * 删除考试分类信息
     * 
     * @param categoryId 考试分类主键
     * @return 结果
     */
    @Override
    public int deleteHawkeyeExamCategoryByCategoryId(Long categoryId)
    {
        return hawkeyeExamCategoryMapper.deleteHawkeyeExamCategoryByCategoryId(categoryId);
    }
}
