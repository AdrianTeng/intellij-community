/*
 * Copyright 2000-2010 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.codeInsight.completion;

import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.JavaDocTokenType;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaCodeReferenceElement;
import com.intellij.psi.javadoc.PsiDocTag;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ThreeState;
import org.jetbrains.annotations.NotNull;

/**
 * @author peter
 */
public class JavadocCompletionConfidence extends CompletionConfidence {

  @NotNull
  @Override
  public ThreeState shouldSkipAutopopup(@NotNull PsiElement contextElement, @NotNull PsiFile psiFile, int offset) {
    if (PsiTreeUtil.findElementOfClassAtOffset(psiFile, offset - 1, PsiDocTag.class, false) != null &&
        PsiTreeUtil.findElementOfClassAtOffset(psiFile, offset - 1, PsiJavaCodeReferenceElement.class, false) != null) {
      return ThreeState.NO;
    }
    if (PlatformPatterns.psiElement(JavaDocTokenType.DOC_TAG_NAME).accepts(contextElement)) {
      return ThreeState.NO;
    }
    return super.shouldSkipAutopopup(contextElement, psiFile, offset);
  }
}
