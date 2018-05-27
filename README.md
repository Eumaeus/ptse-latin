# Plain Text Scholarly Editions - Latin

This library implements methods for validating plain-text scholarly editions in Latin.

## Current version: 0.1.0

Status:  **active development**. [Release notes](releases.md)

## License

[GPL 3.0](https://opensource.org/licenses/gpl-3.0.html)

## Documentation

### Plain-text scholarly editions

Markup languages such as XML are an excellent way to record complex information related to a text, including alternative or multiple possible readings.   At some point, however, a systematic reading or analysis of a single published edition requires a coherent and unambiguous single version of a document.  A single XML document can then serve as the source for multiple plain-text documents.

### Citation

-   Scholarly editions are canonically citable.  We follow the OHCO2 model for citable scholarly texts.

### Contents of citable nodes

-  adhere to a specified orthography
-  this orthography has  specified semantics
-  the semantics of the orthographic system permit us to recognize semantic tokens and to classify tokens into categories.  Ideally, each permitted character is valid only for a given category of token.  (E.g., in English, alphabetic, numeric and punctuation characters could be limited to specific types of token.)
-  tokens belonging to a given class can be subjected to further validation.  E.g., numeric characters in Roman numeral notation can be constrained to a specific syntax;  alphabetic characters might be used for lexical tokens that can subsequently be analyzed morphologically.

### Characterizing scholarly documents

In assembling a repository of scholarly documents, we want to characterize each document systmatically with a series of observations that both measure how completely  the documents validates against its editorial standards, and provide significant information about the the relation of the document to its language and writing system.

These systematic observations include:

-   frequency distribution of each permitted character in the orthographic system
-   a tokenized analytical edition citable at one further level of both the CTS URN work and passage hierarchy
-   frequency distribution of tokens by class as defined by the orthographic system
-   for each relevant type, secondary analysis of each token.  Numbers can be parsed numerically, and lexical tokens parsed morphologically, for example.


## Principles

- Don't do anything without counting it.
- Whitespace is semantically insignificant except for separating token-blocks. (A "token-block" is one-or-more tokens; a block, "est.", consists of two tokens, the lexical token "est" and the punctuation-token ".".)

### Validation of a PTSE

1. Validate a corpus as a citable structure
2. All text-contents are valid within the specified writing system.
3. Classified tokenization:
	- lexical token:
	- punctuation:
	- numeric token:
	- invalid token:
4. Subsequent type-specific analysis/validation, *e.g.* morphology, parsing and composing numbers

