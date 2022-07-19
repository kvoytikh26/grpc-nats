package com.demo.project_reactor

data class User(val username: String?, val firstname: String?, val lastname: String?, val age: Int?) {
    companion object {
        val SKYLER = User("swhite", "Skyler", "White", 23)
        val JESSE = User("jpinkman", "Jesse", "Pinkman", 20)
        val WALTER = User("wwhite", "Walter", "White", 27)
        val SAUL = User("sgoodman", "Saul", "Goodman", 28)
    }
}