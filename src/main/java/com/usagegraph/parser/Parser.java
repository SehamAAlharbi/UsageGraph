package com.usagegraph.parser;

import java.io.File;
import java.io.FileNotFoundException;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

public class Parser {

	public void parseFiles(String directoryPath, String src_path) throws FileNotFoundException {

		TypeSolver reflectionTypeSolver = new ReflectionTypeSolver();
		TypeSolver javaParserTypeSolver = new JavaParserTypeSolver(new File(src_path));

		CombinedTypeSolver combinedSolver = new CombinedTypeSolver();
		combinedSolver.add(reflectionTypeSolver);
		combinedSolver.add(javaParserTypeSolver);

		JavaSymbolSolver symbolSolver = new JavaSymbolSolver(combinedSolver);
		StaticJavaParser.getConfiguration().setSymbolResolver(symbolSolver);

		File directory = new File(directoryPath);
		File[] directoryListing = directory.listFiles();

		if (directoryListing != null) {
			for (File child : directoryListing) {
				
				// Parse each child file
				CompilationUnit cu = StaticJavaParser.parse(child);
				
				// Get method call expressions 
				cu.findAll(MethodCallExpr.class).forEach(mce -> {
					System.out.println(mce.toString());

				});

			}
		} else {
			System.out.println("Directory Does Not exist!");
		}

	}

}
