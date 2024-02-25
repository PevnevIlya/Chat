package com.example.data.implementations

import com.example.data.interfaces.ChatInterface
import com.example.data.model.ChatModel
import com.example.data.model.MessageModel
import com.example.utils.CHATS_COLLECTION
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class ChatInterfaceImpl(
    private val db: CoroutineDatabase
): ChatInterface {
    override suspend fun getAllMessages(chatId: String): MutableList<MessageModel> {
        val chatsCollection = db.getCollection<ChatModel>(CHATS_COLLECTION)
        val chat = chatsCollection.findOne(ChatModel::id eq chatId)
        return chat?.messages ?: mutableListOf()
    }

    override suspend fun insertMessage(chatId: String, message: MessageModel) {
        val chatsCollection = db.getCollection<ChatModel>(CHATS_COLLECTION)
        val chat = chatsCollection.findOne(ChatModel::id eq chatId)
        if(chat != null) {
            chat.messages.add(message)
            chatsCollection.updateOne(ChatModel::id eq chatId, chat)
        }
    }
}