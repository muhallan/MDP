package com.teamvan.assignment4

class User(val firstname: String, val lastname: String, val username: String, val password: String) : java.io.Serializable {

    override fun toString(): String {
        return "User {firstname=$firstname, lastname=$lastname, username=$username, password=$password}"
    }
}
