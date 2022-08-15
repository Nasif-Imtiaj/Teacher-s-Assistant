package com.ni.database

import io.realm.annotations.PrimaryKey
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.InitialResults
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.notifications.UpdatedResults
import io.realm.kotlin.query.RealmQuery
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.RealmObject
import kotlinx.coroutines.flow.collect


class helperDB {
    private val config = RealmConfiguration.Builder(schema = setOf(Task::class))
        .build()
    val realm = Realm.open(config)

    fun write(){
        val person = Person().apply {
            name = "carlo"
            dog = Dog().apply {
                name = "Fido"
                age = 12
            }
        }
        val managedPerson = realm.writeBlocking {
             copyToRealm(person)
        }
    }

    suspend fun writeAsync(){
        val person = Person().apply {
            name = "carlo"
            dog = Dog().apply {
                name = "Fido"
                age = 12
            }
        }
        realm.write {
            copyToRealm(person)
        }
    }

    fun query(){
        val all: RealmResults<Person> = realm.query<Person>().find()
        realm.query<Person>("name = $0", "Carlo").first().find()
    }

    suspend fun queryAsync(){
        realm.query<Person>("dog.age>$0 AND dog.name BEGINSWITH $1",7,"Fi")
            .asFlow()
            .collect{results: ResultsChange<Person> ->
                when(results){
                    is InitialResults<Person> -> println("Initial results size ${results.list.size}")
                    is UpdatedResults<Person> -> println("Updated results changes ${results.changes}" +
                        " deletes ${results.deletions} insertions ${results.insertions}")
                }
            }
    }

    suspend fun update(){
        realm.query<Person>("dog == NULL LIMIT(1)")
            .first()
            .find()
            ?.also { personWithoutDog ->
                realm.write { findLatest(personWithoutDog)?.dog = Dog().apply {
                    name = "Laika"
                } }
            }
    }

    suspend fun delete(){
        realm.write {
            val q : RealmQuery<Dog> = this.query<Dog>()
            delete(q)


        }
    }


}

class Task : RealmObject{
    var name: String = "new task"
    var status: String = "Open"
}
class Dog : RealmObject{
    var name: String=""
    var age: Int = 0
}
class Person: RealmObject{
    @PrimaryKey
    var name = ""
    var dog : Dog? = null
}
