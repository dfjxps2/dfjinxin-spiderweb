<template>
  <div class="work-main">
    <WorkMainHeader :class="headerClass" :headerItems="headerItems" :noNeedHome="noNeedHome"></WorkMainHeader>
    <WorkMainContext :class="contextClass">
      <slot></slot>
    </WorkMainContext>
  </div>
</template>

<script>
  import WorkMainHeader from "@/models/public/WorkMainHeader"
  import WorkMainContext from "@/models/public/WorkMainContext"
  export default {
    name: 'WorkMain',
    components: {
      WorkMainHeader,
      WorkMainContext
    },
    props:{
      headerItems:{
        type:Array
      },
      noNeedHome:{
        type:Boolean
      },
      headerClass:{
        type:String
      },
      contextClass:{
        type:String
      }
    },
    data() {
        return {}
    },
    methods: {}
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .work-main{
    width:100%;
    height:100%;
    overflow-y:auto;
    /*overflow: hidden;
    margin:0 20px 0 20px;*/
  }
</style>
