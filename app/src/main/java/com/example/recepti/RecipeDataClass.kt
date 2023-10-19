package com.example.recepti

import java.io.Serializable

class RecipeDataClass: Serializable {
    var id: String=""
    var dataTitle:String?=null
    var dataIngredients: String?=null
    var dataPreparation: String?=null
    var dataImage: String?=null

    constructor(dataTitle : String?, dataIngredients : String?, dataPreparation : String?, dataImage : String?){
        this.dataTitle=dataTitle
        this.dataIngredients=dataIngredients
        this.dataPreparation=dataPreparation
        this.dataImage=dataImage
    }

    constructor(){ }

}
