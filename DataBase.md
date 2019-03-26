

## Data Base Sqlite

Vamos a construir una base de datos local llamada DBNote y tendremos una table llamada tb_note

- Lo primero es crear la entidad

```kotlin
class BNote(val id:Int?, val name:String?,val description:String?) {

    override fun toString(): String {
        return "BNote(id=$id, name=$name, description=$description)"
    }
}
```
Luego una clase que gestione la creación de la base de datos y las tablas.

```kotlin
class BNoteDatabase(context: Context): SQLiteOpenHelper(context, DATABASE_NAME,null,DATABASE_VERSION)  {

    companion object {
        const val DATABASE_VERSION:Int=1
        const val DATABASE_NAME:String="BDBasicNote"

        //note table
        const val BNOTE_TABLE_NAME="tb_notes"
        const val BNOTE_KEY_ID:String= "id"
        const val BNOTE_KEY_NAME:String= "name"
        const val BNOTE_KEY_DESC:String= "desc"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val sql = ("CREATE TABLE ${BNOTE_TABLE_NAME} ("
                + "${BNOTE_KEY_ID} INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , ${BNOTE_KEY_NAME}  TEXT,"
                + "${BNOTE_KEY_DESC} TEXT )")

        Log.v("CONSOLE","sql $sql")
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val sql = "DROP TABLE IF EXISTS ${BNOTE_TABLE_NAME}"
        db?.execSQL(sql)
    }
}
```
Despues de tener la entidad y la clase SqliteOpenHelper , vamos a construir un clase que maneje las operación en esa tabla (CRUD)

```kotlin
class BNoteRepository(val noteDatabase: BNoteDatabase) {

    fun notes():List<BNote>{
        val sqliteDatabase= noteDatabase.readableDatabase //modo lectura

        val notes:MutableList<BNote> = mutableListOf()
        val sql= "SELECT  * FROM ${BNoteDatabase.BNOTE_TABLE_NAME}"
        val cursor= sqliteDatabase.rawQuery(sql,null)
        if(cursor.moveToFirst()){
            do{
                val note= BNote(cursor.getString(0).toInt(),
                        cursor.getString(1),
                        cursor.getString(2))
                notes.add(note)
            }while(cursor.moveToNext())
        }
        sqliteDatabase.close()
        return notes.toList()
    }

    fun addNote(note:BNote){
        val sqliteDatabase= noteDatabase.writableDatabase //modo escritura

        val contentValues= ContentValues()
        contentValues.put(BNoteDatabase.BNOTE_KEY_NAME,note.name)
        contentValues.put(BNoteDatabase.BNOTE_KEY_DESC,note.description)

        sqliteDatabase.insert(BNoteDatabase.BNOTE_TABLE_NAME,null,contentValues)
        sqliteDatabase.close()
    }
}
```
En esta clase tendremos las operaciones : Listar , insertar, borrar, actualizar.

Listar los elementos de una tabla
```kotlin
  fun notes():List<Note>{
        val sqliteDatabase= noteDatabase.readableDatabase //modo lectura

        val notes:MutableList<Note> = mutableListOf()
        val sql= "SELECT  * FROM ${NoteTable.NAME}"
        val cursor= sqliteDatabase.rawQuery(sql,null)
        if(cursor.moveToFirst()){
            do{
                val note= Note(cursor.getString(0).toInt(),
                        cursor.getString(1),
                        cursor.getString(2))
                notes.add(note)
            }while(cursor.moveToNext())
        }
        sqliteDatabase.close()
        return notes.toList()
    }
```
Agregar un registro

```kotlin
   fun addNote(note:Note){
        val sqliteDatabase= noteDatabase.writableDatabase //modo escritura

        val contentValues=ContentValues()
        contentValues.put(NoteTable.KEY_NAME,note.name)
        contentValues.put(NoteTable.KEY_DESC,note.description)

        sqliteDatabase.insert(NoteTable.NAME,null,contentValues)
        sqliteDatabase.close()
    }
````

Eliminar un registro

```kotlin
    fun deleteNote(note:Note):Int{
        val sqliteDatabase= noteDatabase.writableDatabase //modo escritura
        val row= sqliteDatabase.delete(NoteTable.NAME,
                "${NoteTable.KEY_ID}=?", arrayOf(note.id.toString()))
        sqliteDatabase.close()
        return row
    }
````

Editar un registro

```kotlin
    fun updateNote(note:Note):Int{
        val sqliteDatabase= noteDatabase.writableDatabase //modo escritura
        val contentValues=ContentValues()
        contentValues.put(NoteTable.KEY_NAME,note.name)
        contentValues.put(NoteTable.KEY_DESC,note.description)

        val row= sqliteDatabase.update(NoteTable.NAME,contentValues,
                "${NoteTable.KEY_ID}=?", arrayOf(note.id.toString()))
        sqliteDatabase.close()
        return row
    }
````

- Implementación

```kotlin
    val noteRepository= BNoteRepository(BNoteDatabase(this))

        //agregar notas
        noteRepository.addNote(BNote(null,"Nota 1", "Nota 1"))
        noteRepository.addNote(BNote(null,"Nota 2", "Nota 2"))


        //listar notas
        val notes:List<BNote> = noteRepository.notes()
        notes.forEach {
            Log.v("CONSOLE", "note $it")
            sb.appendln("note $it")
        }
        textView.text= sb.toString()


        //eliminar nota
        //noteRepository.deleteNote()
        /*val dNote= Note(4,"Nota 2","Nota 2")
        val row= noteRepository.deleteNote(dNote)
        Log.v("CONSOLE", "row $row")*/

        //editar nota
        /*val uNote= Note(3,"Nota 3","Desc Nota 3")
        val row=noteRepository.updateNote(uNote)
        Log.v("CONSOLE", "row update $row")*/
````

## Resources 

- Save Key-Value Data with SharedPreferences https://developer.android.com/training/data-storage/shared-preferences.html#java

- Save Data using SQLite https://developer.android.com/training/data-storage/sqlite.html

- Storage Options https://developer.android.com/guide/topics/data/data-storage.html

- Saving Files https://developer.android.com/training/data-storage/files.html

- Data and File Storage Overview https://developer.android.com/guide/topics/data/data-storage.html

- SharedPreferences https://developer.android.com/training/data-storage/shared-preferences.html

- Uplabs https://www.uplabs.com/android

