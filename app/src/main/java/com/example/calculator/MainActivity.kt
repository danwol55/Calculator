package com.example.calculator

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Math.pow
import java.util.*
import kotlin.coroutines.Continuation
import kotlin.math.*

class MainActivity : AppCompatActivity()
{
    var maintainValue: Double = 0.0
    var secondValue: Double = 0.0
    var memory: Double = 0.0
    var result: Double = 0.0
    var hasFullBraces = true
    var math = StringBuilder("")
    var math1 = StringBuilder("")
    val expression = StringBuilder("")
    var mathSymbol: Char = '+'

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null)
        {
            val firstLayout = savedInstanceState.getString("firstLayout")
            val secondLayout = savedInstanceState.getDouble("secondLayout").toString()
            if (!firstLayout.equals("")) textView1.text = firstLayout
            if (secondLayout.equals("0.0")) textView2.text = "0"
            else textView2.text = secondLayout
        }

        fun mathAppend(n: String)
        {
            math.append(n)
            textView1.text = math
            if (hasFullBraces)
            {
                math1.append(math)
                textView2.text = read(math1)
                math1.clear()
            }
        }

        fun check(): Boolean
        {
            for (i in math.indices)
            {
                if (math.length == 1 && math[0] == '0')
                {
                    return false
                }

                if (math[i].isMath())
                {
                    if (i < math.length - 1 && math[i + 1] == '0')
                    {
                        return math[i + 2] == '.'
                    }
                }
            }
            return true
        }
        fun replaceTrigonometric(exp: String)
        {
            if(math.length>0)
            {
                var start = 0

                if(math[math.length-1].isDigit())
                {
                    for (i in math.length - 1 downTo 0)
                    {
                        if (math[i].isDigit()) continue
                        else if(!math[i].isDigit())
                        {
                            start = i + 1
                            break
                        }
                    }
                    if(exp=="sin") math.insert(start, "sin(")
                    if(exp=="cos") math.insert(start, "cos(")
                    if(exp=="tan") math.insert(start, "tan(")
                    if(exp=="ctg") math.insert(start, "ctg(")
                    mathAppend(")")
                }
            }
        }

        button0.setOnClickListener()
        {
            if (check()) mathAppend("0")
        }
        button1.setOnClickListener()
        {
            mathAppend("1")
        }
        button2.setOnClickListener()
        {
            mathAppend("2")
        }
        button3.setOnClickListener()
        {
            mathAppend("3")
        }
        button4.setOnClickListener()
        {
            mathAppend("4")
        }
        button5.setOnClickListener()
        {
            mathAppend("5")
        }
        button6.setOnClickListener()
        {
            mathAppend("6")
        }
        button7.setOnClickListener()
        {
            mathAppend("7")
        }
        button8.setOnClickListener()
        {
            mathAppend("8")
        }
        button9.setOnClickListener()
        {
            mathAppend("9")
        }
        leftBrace?.setOnClickListener()
        {
            hasFullBraces = false
            mathAppend("(")
        }
        rightBrace?.setOnClickListener()
        {
            hasFullBraces = true
            mathAppend(")")
        }
        oneByX?.setOnClickListener()
        {
            if(textView2.text != "0")
            {
                var value = ""
                var hasMaths = false
                if(math[math.length-1].isDigit())
                {
                    for(i in math.indices)
                    {
                        if(math[i].isDigit())
                        {
                            continue
                        }
                        else
                        {
                            hasMaths = true
                            break
                        }
                    }
                    if(hasMaths)
                    {
                        for (i in math.length - 1 downTo 0)
                        {
                            if (math[i].isMath())
                            {
                                value = math.substring(i + 1, math.length)
                                math.delete(i + 1, math.length)
                                break
                            }
                        }
                        mathAppend("1/$value")
                    }
                    else
                    {
                        math.insert(0, "1/")
                        mathAppend("")
                    }
                }
            }
        }
        powX2?.setOnClickListener()
        {
            if(textView2.text != "0") mathAppend("^2")
        }
        powX3?.setOnClickListener()
        {
            if(textView2.text != "0") mathAppend("^3")
        }
        powXY?.setOnClickListener()
        {
            if(textView2.text != "0") mathAppend("^")
        }
        sqrt2?.setOnClickListener()
        {
            math.append('√')
            textView1.text = math
        }
        sqrt3?.setOnClickListener()
        {
            math.append('∛')
            textView1.text = math
        }
        strong?.setOnClickListener()
        {
            if(textView2.text != "0") mathAppend("!")
            else mathAppend("0!")
        }


        //function buttons////////////////////////////////////

        plusButton.setOnClickListener()
        {
            if (math.isNotEmpty())
            {
                if (math.get(math.length - 1).isMath())
                {
                    math.deleteCharAt(math.length - 1)
                    math.append('+')
                    textView1.text = math
                }
                else
                {
                    math.append('+')
                    textView1.text = math
                }
            }
        }
        minusButton.setOnClickListener()
        {
            if (math.isNotEmpty())
            {
                if (math.get(math.length - 1).isMath())
                {
                    math.deleteCharAt(math.length - 1)
                    math.append('-')
                    textView1.text = math
                }
                else
                {
                    math.append('-')
                    textView1.text = math
                }
            }
            else
            {
                math.append('-')
                textView1.text = math
            }
        }
        multipleButton.setOnClickListener()
        {
            if (math.isNotEmpty())
            {
                if (math.get(math.length - 1).isMath())
                {
                    math.deleteCharAt(math.length - 1)
                    math.append('*')
                    textView1.text = math
                }
                else
                {
                    math.append('*')
                    textView1.text = math
                }
            }
        }
        divideButton.setOnClickListener()
        {
            if (math.isNotEmpty())
            {
                if (math.get(math.length - 1).isMath())
                {
                    math.deleteCharAt(math.length - 1)
                    math.append('/')
                    textView1.text = math
                }
                else
                {
                    math.append('/')
                    textView1.text = math
                }
            }
        }
        equalButton.setOnClickListener()
        {
            math1.append(math)
            textView1.text = math
            textView2.text = "=" + read(math1)
            val orientation = resources.configuration.orientation
            if(orientation == Configuration.ORIENTATION_PORTRAIT)
            {
                textView2.textSize = 70f
            }
            if(orientation == Configuration.ORIENTATION_LANDSCAPE)
            {
                textView2.textSize = 40f
            }
            math1.clear()

        }
        dotButton.setOnClickListener()
        {
            if (math.isNotEmpty())
            {
                math.append('.')
                textView1.text = math
            }
            else if(textView2.text == "0")
            {
                math.append("0.")
                textView1.text = math
                textView2.append(".")
            }
        }

        percentButton.setOnClickListener()
        {
            if (math.isNotEmpty())
            {
                mathAppend("%")
            }
        }

        cancelButton.setOnClickListener()
        {
            val orientation = resources.configuration.orientation
            if(orientation == Configuration.ORIENTATION_PORTRAIT)
            {
                textView2.textSize = 60f
            }
            if(orientation == Configuration.ORIENTATION_LANDSCAPE)
            {
                textView2.textSize = 30f
            }
            hasFullBraces = true
            textView2.setText("0")
            textView1.setText("")
            maintainValue = 0.0
            math.clear()

        }

        backButton.setOnClickListener()
        {
            if (math.isNotEmpty())
            {
                math.deleteCharAt(math.length - 1)
                textView1.text = math
                if (hasFullBraces)
                {
                    math1.append(math)
                    textView2.text = read(math1)
                    math1.clear()
                }
            }

        }

        sinus?.setOnClickListener()
        {
            replaceTrigonometric("sin")
        }
        cosinus?.setOnClickListener()
        {
            replaceTrigonometric("cos")
        }
        tangens?.setOnClickListener()
        {
            replaceTrigonometric("tan")
        }
        cotangens?.setOnClickListener()
        {
            replaceTrigonometric("ctg")
        }
        logarithm?.setOnClickListener()
        {
            math.append("log")
            textView1.text = math
        }
        exp?.setOnClickListener()
        {
            math.append("exp")
            textView1.text = math
        }
        mod?.setOnClickListener()
        {
            math.append("mod")
            textView1.text = math
        }
        pi?.setOnClickListener()
        {
            math.append("3.14159265")
            if(math.toString().equals("3.14159265"))
            {
                textView2.text = math
            }
            textView1.text = math
        }
        e?.setOnClickListener()
        {
            math.append("2.71828183")
            if(math.toString().equals("2.71828183"))
            {
                textView2.text = math
            }
            textView1.text = math
        }

        mClear?.setOnClickListener {
            mem?.text = ""
            memory = 0.0
        }
        mPlus?.setOnClickListener {
            mem?.text = "M"
            val text = textView2.text
            memory += text.toString().toDouble()
        }
        mMinus?.setOnClickListener {
            val text = textView2.text
            memory -= text.toString().toDouble()
        }
        mRecall?.setOnClickListener {
            if(isDouble(memory)) textView2.text = memory.toString()
            else textView2.text = memory.toLong().toString()
        }

    }

    @Override
    override fun onSaveInstanceState(outState: Bundle)
    {
        super.onSaveInstanceState(outState)

        val fromFirstLayout = textView1.text.toString()
        val fromScecondLayout = textView2.text.toString().toDouble()

        outState.putString("firstLayout", fromFirstLayout)
        outState.putDouble("secondLayout", fromScecondLayout)
    }

    fun replaceValue(v: Double, s: Char)
    {
        for (i in math.indices)
        {
            if (math.get(i) == s)
            {
                for (j in i downTo 0)
                {
                    if (!math.get(j).isDigit() && math.get(j) != s)
                    {
                        if (isDouble(v)) math.replace(j + 1, i + 1, v.toString())
                        else math.replace(j + 1, i + 1, v.toLong().toString())
                        break
                    }
                }
                break
            }
        }
    }

    fun read(math1: StringBuilder): String
    {
        var firstBuilder = true
        var secondBilder = false
        val builder1 = StringBuilder("")
        val builder2 = StringBuilder("")
        val builder3 = StringBuilder("")

        fun calculate(e: StringBuilder): String
        {
            var start = 0
            var result: Double = 0.0
            var mathLength = e.length

            fun reset()
            {
                builder1.clear()
                builder2.clear()
                builder3.clear()
                firstBuilder = true
                secondBilder = false
            }

            if(e.contains('√') || e.contains('∛'))
            {
                val builder = StringBuilder("")
                var builderActive = false

                while(e.contains('√') || e.contains('∛'))
                {

                    for (i in e.indices)
                    {
                        if (builderActive)
                        {
                            if (e[i].isDigit() || e[i]=='.') builder.append(e.get(i))
                            else
                            {
                                var result1 = 0.0
                                if(e.contains('√')) result1 = sqrt(builder.toString().toDouble())
                                if(e.contains('∛')) result1 = (builder.toString().toDouble()).pow(0.33)
                                val result2 = String.format(Locale.US, "%.2f", result1).toDouble()
                                if (isDouble(result2)) e.replace(start, i, result2.toString())
                                else e.replace(start, i, result2.toLong().toString())
                                builderActive = false
                                builder.clear()
                                break
                            }
                            if(i == e.length-1)
                            {
                                var result1 = 0.0
                                if(e.contains('√')) result1 = sqrt(builder.toString().toDouble())
                                if(e.contains('∛')) result1 = (builder.toString().toDouble()).pow(0.33)
                                val result2 = String.format(Locale.US, "%.2f", result1).toDouble()
                                if (isDouble(result2)) e.replace(start, i+1, result2.toString())
                                else e.replace(start, i+1, result2.toLong().toString())
                                builderActive = false
                                builder.clear()
                                break
                            }
                        }
                        else
                        {
                            if (e.get(i) == '√' || e.get(i) == '∛')
                            {
                                if(e.get(i+1)=='√' || e.get(i+1)=='∛')
                                {
                                    continue
                                }
                                if (i>0 && e.get(i - 1).isDigit())
                                {
                                    e.insert(i, '*')
                                    break
                                }
                                builderActive = true
                                start = i
                                continue
                            }
                        }
                    }
                }
            }

            fun calculate1(i: Int)
            {
                when (mathSymbol)
                {
                    '+' -> result =
                        builder1.toString().toDouble() + builder2.toString().toDouble()
                    '-' -> result =
                        builder1.toString().toDouble() - builder2.toString().toDouble()
                    '*' -> result =
                        builder1.toString().toDouble() * builder2.toString().toDouble()
                    '/' ->
                    {
                        if (builder2.toString() != "0") result =
                            builder1.toString().toDouble() / builder2.toString().toDouble()
                        else textView2.text = "error!"
                    }
                    '^' -> result =
                        builder1.toString().toDouble().pow(builder2.toString().toDouble())
                }

                start = i - (builder1.length + 1 + builder2.length)
                val result1 = String.format(Locale.US, "%.2f", result).toDouble()

                if (start >= 0)
                {
                    if (isDouble(result1)) e.replace(start, i, result1.toString())
                    else e.replace(start, i, result1.toLong().toString())
                    mathLength = e.length
                }
                else if (start < 0)
                {
                    start = 0
                    if (isDouble(result1)) e.replace(start, i, result1.toString())
                    else e.replace(start, i, result1.toLong().toString())
                    mathLength = e.length

                }

            }

            fun replace1(value1: Double, i: Int)
            {
                if (isDouble(value1)) e.replace(i - builder1.length, i + 1, value1.toString())
                else e.replace(i - builder1.length, i + 1, value1.toLong().toString())
            }

            fun replace2(value1: Double, i: Int)
            {
                if (isDouble(value1)) e.replace(i - builder2.length, i + 1, value1.toString())
                else e.replace(i - builder2.length, i + 1, value1.toLong().toString())
            }
            fun logarithm(i: Int)
            {
                start = i-(builder1.length+2+builder2.length)
                val result1 = log(builder1.toString().toFloat(), builder2.toString().toFloat())
                if(isDouble(result1.toDouble())) e.replace(start, i+1, result1.toString())
                else e.replace(start, i+1, result1.toLong().toString())
                textView2.text = e
            }
            fun modulo(i: Int)
            {
                start = i-(builder1.length+2+builder2.length)
                val result1 = (builder1.toString().toFloat()%builder2.toString().toFloat())
                if(isDouble(result1.toDouble())) e.replace(start, i+1, result1.toString())
                else e.replace(start, i+1, result1.toLong().toString())
                textView2.text = e
            }
            fun exponent(i: Int)
            {
                start = i-(builder1.length+2+builder2.length)
                val result1 = builder1.toString().toDouble()* 10.0.pow(builder2.toString().toDouble())
                if(isDouble(result1)) e.replace(start, i+1, result1.toString())
                else e.replace(start, i+1, result1.toLong().toString())
                textView2.text = e
            }
            fun strong(n: Int):Long
            {
                if(n < 2) return 1
                else return n * strong(n-1)
            }

            while (containsMath(e))
            {

                for (i in 0 until e.length)
                {

                    if (e.get(i).isDigit() || e.get(i) == '.')
                    {
                        if (firstBuilder) builder1.append(e.get(i))
                        else builder2.append(e.get(i))
                    }
                    if(e.get(i).isLetter()) builder3.append(e.get(i))

                    if (e.contains("--"))
                    {
                        while (e.contains("--"))
                        {
                            for (j in 0 until e.length)
                            {
                                if (e.get(j) == '-' && e.get(j + 1) == '-')
                                {
                                    e.replace(j, j + 2, "+")
                                    mathLength = e.length
                                    break
                                }
                            }
                        }
                    }
                    else if (e.contains("+-"))
                    {
                        while (e.contains("+-"))
                        {
                            for (j in 0 until e.length)
                            {
                                if (e.get(j) == '+' && e.get(j + 1) == '-')
                                {
                                    e.replace(j, j + 2, "-")
                                    mathLength = e.length
                                    break
                                }
                            }
                        }
                    }
                    else if(e.contains("exp"))
                    {
                        if(e[i].isMath())
                        {
                            if(!builder3.toString().equals("exp"))
                            {
                                reset()
                                continue
                            }
                        }
                        if(builder3.toString().equals("exp"))
                        {
                            if(!secondBilder)
                            {
                                if(builder1.length>0)
                                {
                                    firstBuilder = false
                                    secondBilder = true
                                    continue
                                }
                            }
                            if(builder2.length>0)
                            {
                                exponent(i)
                                reset()
                                break
                            }
                        }

                    }
                    else if(e.contains("mod"))
                    {
                        if(e[i].isMath())
                        {
                            if(!builder3.toString().equals("mod"))
                            {
                                reset()
                                continue
                            }
                        }
                        if(builder3.toString().equals("mod"))
                        {
                            if(!secondBilder)
                            {
                                if(builder1.length>0)
                                {
                                    firstBuilder = false
                                    secondBilder = true
                                    continue
                                }
                            }
                            if(builder2.length>0)
                            {
                                modulo(i)
                                reset()
                                break
                            }
                        }

                    }
                    else if(e.contains("log"))
                    {
                        if(e[i].isMath())
                        {
                            if(!builder3.toString().equals("log"))
                            {
                                reset()
                                continue
                            }
                        }
                        if(builder3.toString().equals("log"))
                        {
                            if(!secondBilder)
                            {
                                if(builder1.length>0)
                                {
                                    firstBuilder = false
                                    secondBilder = true
                                    continue
                                }
                            }
                            if(builder2.length>0)
                            {
                                logarithm(i)
                                reset()
                                break
                            }
                        }

                    }
                    else if(e.contains('!'))
                    {
                        if(e.get(i).isMath())
                        {
                            start = i+1
                            builder1.clear()
                            continue
                        }
                        else if(e.get(i)=='!')
                        {
                            val value = builder1.toString().toInt()
                            e.replace(start, i+1, strong(value).toString())
                            reset()
                            break
                        }
                    }
                    else if (e.contains('^'))
                    {
                        if (e.get(i).isMath())
                        {
                            if (e.get(i) == '^')
                            {
                                if (builder2.length > 0)
                                {
                                    calculate1(i)
                                    reset()
                                    break
                                }
                                else
                                {
                                    firstBuilder = false
                                    secondBilder = true
                                    mathSymbol = e.get(i)
                                }
                            }
                            else
                            {
                                if (builder2.length > 0)
                                {
                                    calculate1(i)
                                    reset()
                                    break
                                }
                                else
                                {
                                    reset()
                                    continue
                                }
                            }
                        }
                    }
                    else if (e.contains('*') || e.contains('/'))
                    {
                        if (e.get(i) == '*' || e.get(i) == '/')
                        {
                            if (builder2.length > 0)
                            {
                                calculate1(i)
                                reset()
                                break
                            }
                            else
                            {
                                firstBuilder = false
                                secondBilder = true
                                mathSymbol = e.get(i)
                            }
                        }
                        if (e.get(i) == '+' || e.get(i) == '-')
                        {
                            if (builder1.length > 0)
                            {
                                if (e.get(i) == '-')
                                {
                                    if (e.get(i - 1) == '*' || e.get(i - 1) == '/')
                                    {
                                        builder2.append('-')
                                        continue
                                    }
                                }
                                if (builder2.length > 0)
                                {
                                    calculate1(i)
                                    reset()
                                    break
                                }
                                else
                                {
                                    builder1.clear()
                                    continue
                                }
                            }
                            else if (builder1.length == 0)
                            {
                                if (e.get(i) == '-') builder1.append('-')
                                continue
                            }
                        }
                    }
                    else if (e.contains('+') || e.contains('-'))
                    {

                        if (e.get(i) == '+' || e.get(i) == '-')
                        {
                            if (builder1.length > 0)
                            {
                                if (builder2.length > 0)
                                {
                                    calculate1(i)
                                    reset()
                                    break
                                }
                                else
                                {
                                    firstBuilder = false
                                    secondBilder = true
                                    mathSymbol = e.get(i)
                                }
                            }
                            else if (builder1.length == 0)
                            {
                                if (e.get(i) == '-') builder1.append('-')
                                continue
                            }
                        }

                    }
                    if (e.get(i) == '%')
                    {
                        if (builder1.length > 0)
                        {
                            if (builder2.length > 0)
                            {
                                if (mathSymbol == '+' || mathSymbol == '-')
                                {
                                    var value = builder1.toString().toDouble();
                                    value *= builder2.toString().toDouble() / 100
                                    val value1 = String.format(Locale.US, "%.2f", value).toDouble()
                                    replace2(value1, i)
                                    replaceValue(value1, '%')
                                    textView1.text = math
                                }
                                if (mathSymbol == '*' || mathSymbol == '/')
                                {
                                    val value = builder2.toString().toDouble() / 100
                                    val value1 = String.format(Locale.US, "%.2f", value).toDouble()
                                    replace2(value1, i)
                                    replaceValue(value1, '%')
                                    textView1.text = math
                                }

                            }
                            else
                            {
                                val value = builder1.toString().toDouble() / 100
                                val value1 = String.format(Locale.US, "%.2f", value).toDouble()
                                replace1(value1, i)
                                replaceValue(value1, '%')
                                textView1.text = math
                            }
                            reset()
                            break
                        }
                    }
                    if (e.get(i) == 'X')
                    {
                        if (builder1.length > 0)
                        {
                            if (builder2.length > 0)
                            {
                                val value = 1 / builder2.toString().toDouble()
                                val value1 = String.format(Locale.US, "%.2f", value).toDouble()
                                replace2(value1, i)
                                replaceValue(value1, 'X')
                                textView1.text = math
                            }
                            else
                            {
                                val value = 1 / builder1.toString().toDouble()
                                val value1 = String.format(Locale.US, "%.2f", value).toDouble()
                                replace1(value1, i)
                                replaceValue(value1, 'X')
                                textView1.text = math
                            }
                            reset()
                            break

                        }
                    }
                    if (e.get(i) == 'C')
                    {
                        if (builder1.length > 0)
                        {
                            if (builder2.length > 0)
                            {
                                val value = builder2.toString().toDouble().pow(2.0)
                                val value1 = String.format(Locale.US, "%.2f", value).toDouble()
                                replace2(value1, i)
                                replaceValue(value1, 'C')
                                textView1.text = math
                            }
                            else
                            {
                                val value = builder1.toString().toDouble().pow(2.0)
                                val value1 = String.format(Locale.US, "%.2f", value).toDouble()
                                replace1(value1, i)
                                replaceValue(value1, 'C')
                                textView1.text = math
                            }
                            reset()
                            break

                        }
                    }
                    if (e.get(i) == 'V')
                    {
                        if (builder1.length > 0)
                        {
                            if (builder2.length > 0)
                            {
                                val value = builder2.toString().toDouble().pow(3.0)
                                val value1 = String.format(Locale.US, "%.2f", value).toDouble()
                                replace2(value1, i)
                                replaceValue(value1, 'V')
                                textView1.text = math
                            }
                            else
                            {
                                val value = builder1.toString().toDouble().pow(3.0)
                                val value1 = String.format(Locale.US, "%.2f", value).toDouble()
                                replace1(value1, i)
                                replaceValue(value1, 'V')
                                textView1.text = math
                            }
                            reset()
                            break

                        }
                    }

                    if (i == e.length - 1)
                    {
                        if (e.get(i).isDigit())
                        {
                            if(builder3.toString().equals("log"))
                            {
                                logarithm(i)
                                reset()
                                break
                            }
                            if (builder2.length > 0)
                            {
                                calculate1(i + 1)
                                reset()
                                break
                            }
                        }
                        else if (e.get(i).isMath())
                        {
                            e.deleteCharAt(i)
                            reset()
                        }

                    }


                }

                var counter = 0
                for (j in 0 until e.length)
                {
                    if (e.get(j).isMath()) counter++
                }
                if (counter == 1)
                {
                    if (e.startsWith('-'))
                    {
                        reset()
                        break
                    }
                }

            }



            return e.toString()
        }

        while(math1.contains("sin") || math1.contains("cos")
            || math1.contains("tan") || math1.contains("ctg"))
        {
            var start = 0
            for(i in math1.indices)
            {
                if(math1[i].isLetter()) builder1.append(math1[i])
                if(secondBilder && math1[i].isDigit()) builder2.append(math1[i])
                if(i>0 &&(math1[i]=='s' || math1[i]=='c' || math1[i]=='t')&& math1[i-1].isDigit())
                {
                    math1.insert(i, '*')
                    break
                }
                if(math1[i]=='(')
                {
                    secondBilder = true
                    continue
                }
                if(math1[i]==')')
                {
                    secondBilder = false
                    if(builder1.contains("sin")) result = String.format(Locale.US, "%.2f", sin(builder2.toString().toFloat()*0.0175)).toDouble()
                    if(builder1.contains("cos")) result = String.format(Locale.US, "%.2f", cos(builder2.toString().toFloat()*0.0175)).toDouble()
                    if(builder1.contains("tan")) result = String.format(Locale.US, "%.2f", tan(builder2.toString().toFloat()*0.0175)).toDouble()
                    if(builder1.contains("ctg")) result = String.format(Locale.US, "%.2f", 1/(tan(builder2.toString().toFloat()*0.0175))).toDouble()
                    start = i - (4 + builder2.length)
                    math1.replace(start, i+1, result.toString())
                    builder1.clear()
                    builder2.clear()
                    break
                }
            }
        }

        if (math1.contains('('))
        {
            var leftBraceCounter: Short = 0
            var rightBraceCounter: Short = 0
            for (i in 0 until math.length)
            {
                if (math1.get(i) == '(') leftBraceCounter++
                else if (math1.get(i) == ')') rightBraceCounter++
            }
            if (leftBraceCounter == rightBraceCounter)
            {
                for (i in 0 until math1.length)
                {
                    if (i + 1 < math1.length)
                    {
                        if (math1.get(i).isDigit() && math1.get(i + 1) == '(')
                        {
                            math1.insert(i + 1, '*')
                        }
                        if (math1.get(i) == ')' && math1.get(i + 1).isDigit())
                        {
                            math1.insert(i + 1, '*')
                        }
                    }
                }

                var endPosition = 0
                var startPosition = 0
                while (leftBraceCounter > 0)
                {
                    for (i in 0 until math1.length)
                    {
                        if (math1.get(i) == '(')
                        {
                            if (i < math1.length - 1)
                            {
                                startPosition = i + 1
                            }
                        }
                        if (math1.get(i) == ')')
                        {
                            endPosition = i
                            leftBraceCounter--
                            expression.append(math1.substring(startPosition, endPosition))
                            if (startPosition - 1 >= 0 && endPosition + 1 <= math1.length)
                            {
                                if (startPosition <= 0) startPosition++
                                math1.replace(
                                    startPosition - 1,
                                    endPosition + 1,
                                    calculate(expression)
                                )
                                expression.clear()
                                break
                            }
                        }
                    }
                }
            }
        }

        return calculate(math1)


    }


    fun Char.isMath(): Boolean
    {
        return this == '+' || this == '-' || this == '*' || this == '/'
                || this == '^' || this == '√' || this == '∛'
    }

    fun containsMath(e: StringBuilder): Boolean
    {
        return (e.contains('*') || e.contains('/')
                || e.contains('+') || e.contains('-')
                || e.contains('%') || e.contains('X')
                || e.contains('C') || e.contains('V')
                || e.contains('^') || e.contains('!')
                || e.contains("log") || e.contains("mod")
                || e.contains("exp"))
    }

    fun isDouble(result: Double): Boolean
    {
        val result1 = result - floor(result)
        return result1 != 0.0
    }


}
