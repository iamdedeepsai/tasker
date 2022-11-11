package com.example

import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.spec.InvalidKeySpecException
import java.util.*
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec


object Password {
    private val RANDOM: Random = SecureRandom()
    private const val ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
    private const val ITERATIONS = 10000
    private const val KEY_LENGTH = 256

    private fun getSalt(length: Int): String {
        val returnValue = StringBuilder(length)
        for (i in 0 until length) {
            returnValue.append(ALPHABET[RANDOM.nextInt(ALPHABET.length)])
        }
        return String(returnValue)
    }

    private fun hash(password: CharArray?, salt: ByteArray?): ByteArray {
        val spec = PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH)
        Arrays.fill(password!!, Character.MIN_VALUE)
        return try {
            val skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
            skf.generateSecret(spec).encoded
        } catch (e: NoSuchAlgorithmException) {
            throw AssertionError("Error while hashing a password: " + e.message, e)
        } catch (e: InvalidKeySpecException) {
            throw AssertionError("Error while hashing a password: " + e.message, e)
        } finally {
            spec.clearPassword()
        }
    }

    fun generate(password: String) : String{
        val returnValue: String?
        val salt: String = this.getSalt(16)
        val securePassword = hash(password.toCharArray(), salt.toByteArray())
        returnValue = Base64.getEncoder().encodeToString(securePassword)
        return returnValue
    }

    fun verifyUserPassword(providedPassword: String, securedPassword: String?): Boolean {
        val returnValue: Boolean

        // Generate New secure password with the same salt
        val newSecurePassword = generate(providedPassword)

        // Check if two passwords are equal
        returnValue = newSecurePassword.equals(securedPassword, ignoreCase = true)
        return returnValue
    }
}