/**
 * Created by SongCQ on 2018/10/18.
 */

const menuTurner = {
  modalParse : function (modalJson){
    var menuTmp = new Object()
    var menuList = new Array()
    if(modalJson!=null){
      modalJson.forEach(menuData => {
        const parentMenuId = menuData.super_module_id
        if(parentMenuId=='0'){
          if(menuTmp[menuData.module_id]!=null){
            menuTmp[menuData.module_id] = {"id":menuData.module_id,"name":menuData.module_name,"url":menuData.module_url,"children":menuTmp[menuData.module_id].children}
          }else{
            menuTmp[menuData.module_id] = {"id":menuData.module_id,"name":menuData.module_name,"url":menuData.module_url,"children":[]}
          }
        }else{
          if(menuTmp[parentMenuId]!=null){
            menuTmp[parentMenuId].children.push({"id":menuData.module_id,"name":menuData.module_name,"url":menuData.module_url})
          }else{
            menuTmp[parentMenuId] = {"children":[{"id":menuData.module_id,"name":menuData.module_name,"url":menuData.module_url}]}
          }
        }
      })
    }

    Object.keys(menuTmp).forEach(rootMenuId=>{
      menuList.push(menuTmp[rootMenuId])
    })
    return menuList
  }
}

export default menuTurner
