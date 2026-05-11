import request from '@/utils/request'

// 查询章节管理列表
export function listKnowledgePoint(query) {
  return request({
    url: '/hawkeye/knowledgePoint/list',
    method: 'get',
    params: query
  })
}

// 查询章节管理详细
export function getKnowledgePoint(kpId) {
  return request({
    url: '/hawkeye/knowledgePoint/' + kpId,
    method: 'get'
  })
}

// 新增章节管理
export function addKnowledgePoint(data) {
  return request({
    url: '/hawkeye/knowledgePoint',
    method: 'post',
    data: data
  })
}

// 修改章节管理
export function updateKnowledgePoint(data) {
  return request({
    url: '/hawkeye/knowledgePoint',
    method: 'put',
    data: data
  })
}

// 删除章节管理
export function delKnowledgePoint(kpId) {
  return request({
    url: '/hawkeye/knowledgePoint/' + kpId,
    method: 'delete'
  })
}
