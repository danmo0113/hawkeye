import request from '@/utils/request'

// 查询考试分类列表
export function listCategory(query) {
  return request({
    url: '/hawkeye/category/list',
    method: 'get',
    params: query
  })
}

// 查询考试分类详细
export function getCategory(categoryId) {
  return request({
    url: '/hawkeye/category/' + categoryId,
    method: 'get'
  })
}

// 新增考试分类
export function addCategory(data) {
  return request({
    url: '/hawkeye/category',
    method: 'post',
    data: data
  })
}

// 修改考试分类
export function updateCategory(data) {
  return request({
    url: '/hawkeye/category',
    method: 'put',
    data: data
  })
}

// 保存菜单排序
export function updateCategorySort(data) {
  return request({
    url: '/hawkeye/category/updateSort',
    method: 'put',
    data: data
  })
}

// 删除考试分类
export function delCategory(categoryId) {
  return request({
    url: '/hawkeye/category/' + categoryId,
    method: 'delete'
  })
}
