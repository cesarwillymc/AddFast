package com.summit.android.addfast.utils

object Constants {
    //Api Url
    //Path Files
    const val APP_SETTINGS_PATH_PHOTOS="/storage/emulated/0/summitFood/"

    const val BASE_URL_API="http://64.52.85.15:4000/api/"
    //const val BASE_URL_API="https://678e7a1fa3f5.ngrok.io/api/"
    // const val BASE_URL_API="https://5acec32784b9.ngrok.io/api/"
    const val BASE_URL_AMAZON_IMG="https://summit-puno.s3.us-east-2.amazonaws.com/"
    //Path Files
    //Shared Preferencs TEMPORALES
    const val PREF_TEMP_CARRITO_ID="PREF_TEMP_CARRITO_ID"
    const val PREF_TEMP_NAME_SERVICE="PREF_TEMP_NAME_SERVICE"
    const val PREF_TEMP_LAT="PREF_TEMP_LAT"
    const val PREF_TEMP_LONG="PREF_TEMP_LONG"


    //DD Room
    //Shared Preferencs TITLE
    const val APP_SETTINGS_FILE = "APP_SETTINGS"
    const val APP_SETTINGS_TEMP = "APP_TEMP"
    const val PREF_LOGEADO = "PREF_LOGEADO"

    //Shared Preferencs Val
    const val PREF_TOKEN = "PREF_TOKEN"
    const val PREF_TOKEN_FIREBASE = "PREF_TOKEN_FIREBASE"
    const val PREF_ID_USER = "PREF_ID_USER"
    const val PREF_PEDIDO_PEDIDO = "PREF_PEDIDO_PEDIDO"

    const val PREF_TIME_LASTED = "PREF_TIME_LASTED"
    const val PREF_TIME_PEDIDO = "PEDIDO_FETCH"
    const val PREF_TIME_LASTED_GPS = "PREF_TIME_LASTED_GPS"


    //DD Room
    const val NAME_TABLE_USER="user"
    const val NAME_TABLE_MAPEO = "NAME_TABLE_MAPEO"
    const val NAME_TABLE_CATEGORIES = "NAME_TABLE_CATEGORIES"
    const val NAME_TABLE_SERVICES = "NAME_TABLE_SERVICES"
    const val NAME_PAY_FORM = "NAME_PAY_FORM"
    const val NAME_DATABASE ="TURISMAPP"
    const val NAME_TABLE_CARRITO = "NAME_TABLE_CARRITO"
    const val NAME_TABLE_CARRITO_PRICE = "NAME_TABLE_CARRITO_PRICE"
    const val NAME_TABLE_SERVICE = "NAME_TABLE_SERVICE"
    const val NAME_TABLE_SERVICE_ITEMS = "NAME_TABLE_SERVICE_ITEMS"
    const val NAME_FETCH_HISTORY = "NAME_FETCH_HISTORY"

    //CLave auth gmail
    const val AUTH_TOKEN_GMAIL_ANDROID = "663156256150-6m5nek165arvlp55kdbig9bemei32pv7.apps.googleusercontent.com"
    const val AUTH_TOKEN_GMAIL_WEB = "197162189730-m5bvkvj2g535nq5i72rj19oe5c2jevdb.apps.googleusercontent.com"

    //const val AUTH_TOKEN_GMAIL_WEB="197162189730-hemvuff596d3gsjsj8t7unt364s0nft1.apps.googleusercontent.com"
    val category = listOf(
            "Restaurantes",
            "Licorerias",
            "Mercados",
            "Supermercados",
            "Farmacias"
    )
    const val provinciaJson="""[ { "id": "0401", "name": "Arequipa", "department_id": "04" }, { "id": "0402", "name": "Camana", "department_id": "04" }, { "id": "0403", "name": "Caraveli", "department_id": "04" }, { "id": "0404", "name": "Castilla", "department_id": "04" }, { "id": "0405", "name": "Caylloma", "department_id": "04" }, { "id": "0406", "name": "Condesuyos", "department_id": "04" }, { "id": "0407", "name": "Islay", "department_id": "04" }, { "id": "0408", "name": "La Uniòn", "department_id": "04" }, { "id": "1501", "name": "Lima ", "department_id": "15" }, { "id": "1502", "name": "Barranca ", "department_id": "15" }, { "id": "1503", "name": "Cajatambo ", "department_id": "15" }, { "id": "1504", "name": "Canta ", "department_id": "15" }, { "id": "1505", "name": "Cañete ", "department_id": "15" }, { "id": "1506", "name": "Huaral ", "department_id": "15" }, { "id": "1507", "name": "Huarochiri ", "department_id": "15" }, { "id": "1508", "name": "Huaura ", "department_id": "15" }, { "id": "1509", "name": "Oyon ", "department_id": "15" }, { "id": "1510", "name": "Yauyos ", "department_id": "15" }, { "id": "1601", "name": "Maynas ", "department_id": "16" }, { "id": "1801", "name": "Mariscal Nieto ", "department_id": "18" }, { "id": "1802", "name": "General Sanchez Cerro ", "department_id": "18" }, { "id": "1803", "name": "Ilo ", "department_id": "18" }, { "id": "2101", "name": "Puno ", "department_id": "21" }, { "id": "2102", "name": "Azangaro ", "department_id": "21" }, { "id": "2103", "name": "Carabaya ", "department_id": "21" }, { "id": "2104", "name": "Chucuito ", "department_id": "21" }, { "id": "2105", "name": "El Collao ", "department_id": "21" }, { "id": "2106", "name": "Huancana ", "department_id": "21" }, { "id": "2107", "name": "Lampa ", "department_id": "21" }, { "id": "2108", "name": "Melgar ", "department_id": "21" }, { "id": "2109", "name": "Moho ", "department_id": "21" }, { "id": "2110", "name": "San Antonio de Putina ", "department_id": "21" }, { "id": "2111", "name": "San Roman ", "department_id": "21" }, { "id": "2112", "name": "Sandia ", "department_id": "21" }, { "id": "2113", "name": "Yunguyo ", "department_id": "21" }, { "id": "2301", "name": "Tacna ", "department_id": "23" }, { "id": "2302", "name": "Candarave ", "department_id": "23" }, { "id": "2303", "name": "Jorge Basadre ", "department_id": "23" }, { "id": "2304", "name": "Tarata ", "department_id": "23" } ]"""
    const val departamentoJson="""[ {"id":"04","name":"Arequipa"},  {"id":"15","name":"Lima"},  {"id":"18","name":"Moquegua"}, {"id":"21","name":"Puno"}, {"id":"23","name":"Tacna"}]"""
}