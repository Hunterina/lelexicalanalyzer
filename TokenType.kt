package ru.scratty.parser

enum class TokenType {
    BEGIN,
    END,
    REAL,
    INTEGER,

    COLON,
    SEMICOLON,

    NUMBER,
    WORD,

    PLUS,
    MINUS,
    STAR,
    SLASH,
    EQ,

    LPAREN,
    RPAREN,

    EOF;
}