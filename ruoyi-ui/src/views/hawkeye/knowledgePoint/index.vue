<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="考点名称" prop="kpName">
        <el-input
          v-model="queryParams.kpName"
          placeholder="请输入考点名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="层级" prop="level">
        <el-select v-model="queryParams.level" placeholder="请选择层级" clearable>
          <el-option
            v-for="dict in dict.type.knowledge_point_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
	    <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['hawkeye:knowledgePoint:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
            <el-button type="success" plain icon="el-icon-s-unfold" size="mini" 
            :disabled="single" @click="MockExamsList">章节练习</el-button>
          </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-sort"
          size="mini"
          @click="toggleExpandAll"
        >展开/折叠</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      v-if="refreshTable"
      v-loading="loading"
      :data="knowledgePointList"
      row-key="kpId"
      :default-expand-all="isExpandAll"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column type="selection" width="50" align="center" />
      <el-table-column label="所属分类" prop="categoryName" />
      <el-table-column label="考点名称" align="center" prop="kpName" />
      <el-table-column label="层级" align="center" prop="level">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.knowledge_point_type" :value="scope.row.level"/>
        </template>
      </el-table-column>
      <el-table-column label="建议学习时长" align="center" prop="suggestedDuration" />
      <el-table-column label="排序" align="center" prop="sortOrder" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['hawkeye:knowledgePoint:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleAdd(scope.row)"
            v-hasPermi="['hawkeye:knowledgePoint:add']"
          >新增</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['hawkeye:knowledgePoint:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改章节管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="层级" prop="level">
              <el-select v-model="form.level" placeholder="请选择层级">
                <el-option
                  v-for="dict in dict.type.knowledge_point_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="parseInt(dict.value)"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所属分类" prop="categoryId">
              <el-cascader :props="{ emitPath: false }" v-model="form.categoryId" :options="categoryOptions"></el-cascader>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="父级章节" prop="parentId">
              <treeselect v-model="form.parentId" :options="knowledgePointOptions" :normalizer="normalizer" placeholder="请选择父级章节" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="考点名称" prop="kpName">
              <el-input v-model="form.kpName" placeholder="请输入考点名称" />
            </el-form-item>
          </el-col>
          
          <el-col :span="24">
            <el-form-item label="建议学习时长(分钟)" prop="suggestedDuration">
              <el-input v-model="form.suggestedDuration" placeholder="请输入建议学习时长" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="排序" prop="sortOrder">
              <el-input v-model="form.sortOrder" placeholder="请输入排序" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listKnowledgePoint, getKnowledgePoint, delKnowledgePoint, addKnowledgePoint, updateKnowledgePoint } from "@/api/hawkeye/knowledgePoint"
import { listCategory } from "@/api/hawkeye/category"
import Treeselect from "@riophae/vue-treeselect"
import "@riophae/vue-treeselect/dist/vue-treeselect.css"

export default {
  name: "KnowledgePoint",
  dicts: ['knowledge_point_type'],
  components: {
    Treeselect
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 章节管理表格数据
      knowledgePointList: [],
      // 章节管理树选项
      knowledgePointOptions: [],
      //所属分类树选项
      categoryOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否展开，默认全部展开
      isExpandAll: true,
      // 重新渲染表格状态
      refreshTable: true,
      // 查询参数
      queryParams: {
        categoryId: null,
        kpName: null,
        level: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        categoryId: [
          { required: true, message: "所属分类不能为空", trigger: "change" }
        ],
        kpName: [
          { required: true, message: "考点名称不能为空", trigger: "blur" }
        ],
        level: [
          { required: true, message: "层级不能为空", trigger: "change" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询章节管理列表 */
    getList() {
      this.loading = true
      listKnowledgePoint(this.queryParams).then(response => {
        this.knowledgePointList = this.handleTree(response.data, "kpId", "parentId")
        this.loading = false
      })
    },
    /** 转换章节管理数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children
      }
      return {
        id: node.kpId,
        label: node.kpName,
        children: node.children
      }
    },
    convertTree(originalArray) {
      if (!Array.isArray(originalArray)) return [];
      
      return originalArray.map(item => {
        // 提取需要转换的字段，其余字段保留
        const { categoryId, categoryName, children, ...rest } = item;
        
        const newNode = {
          value: categoryId,
          label: categoryName,
          ...rest
        };
        
        if (children && children.length) {
          newNode.children = this.convertTree(children);
        }
        
        return newNode;
      });
    },
    
	/** 查询章节管理下拉树结构 */
    getTreeselect() {
      listKnowledgePoint().then(response => {
        this.knowledgePointOptions = []
        const data = { kpId: 0, kpName: '顶级节点', children: [] }
        data.children = this.handleTree(response.data, "kpId", "parentId")
        this.knowledgePointOptions.push(data)
      })
    },
    /** 查询分类管理下拉树结构 */
    getCategoryTreeselect() {
      listCategory().then(response => {
        this.categoryOptions = this.convertTree(this.handleTree(response.data, "categoryId", "parentId"));
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        kpId: null,
        categoryId: null,
        parentId: null,
        kpName: null,
        level: null,
        importance: null,
        suggestedDuration: null,
        sortOrder: null,
        status: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      }
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    /** 新增按钮操作 */
    handleAdd(row) {
      this.reset()
      this.getTreeselect()
      this.getCategoryTreeselect()
      if (row != null && row.kpId) {
        this.form.parentId = row.kpId
      } else {
        this.form.parentId = 0
      }
      this.open = true
      this.title = "添加章节管理"
    },
    /** 展开/折叠操作 */
    toggleExpandAll() {
      this.refreshTable = false
      this.isExpandAll = !this.isExpandAll
      this.$nextTick(() => {
        this.refreshTable = true
      })
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      this.getTreeselect()
      this.getCategoryTreeselect()
      if (row != null) {
        this.form.parentId = row.parentId
      }
      getKnowledgePoint(row.kpId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改章节管理"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.kpId != null) {
            updateKnowledgePoint(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addKnowledgePoint(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      this.$modal.confirm('是否确认删除章节管理编号为"' + row.kpId + '"的数据项？').then(function() {
        return delKnowledgePoint(row.kpId)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    }
  }
}
</script>
