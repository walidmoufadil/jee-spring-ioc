# Projet Spring IoC (Injection de Dépendances)

Ce projet démontre l'utilisation du framework Spring pour l'injection de dépendances dans un environnement Java. Le projet couvre différentes techniques d'injection de dépendances, y compris l'injection statique, l'injection dynamique et l'injection via Spring, en utilisant à la fois la configuration XML et les annotations.

## Objectifs du projet

1. Créer l'interface `IDao` avec une méthode `getData`.
2. Créer une implémentation de cette interface.
3. Créer l'interface `IMetier` avec une méthode `calcul`.
4. Créer une implémentation de cette interface en utilisant le principe de **couplage faible**.
5. Appliquer différentes techniques d'injection de dépendances :
    - Par instanciation statique
    - Par instanciation dynamique
    - En utilisant le framework Spring avec une configuration XML et avec des annotations.

---

## Structure du projet

- `src/main/java/`
    - `dao/` : Contient l'interface `IDao` et son implémentation.
    - `metier/` : Contient l'interface `IMetier` et son implémentation avec couplage faible.
    - `presentation/` : Contient la classe principale pour tester l'injection de dépendances.

---

## 1. Interface `IDao` (DAO)

```java
package dao;

public interface IDao {
    double getData();
}
```

---

## 2. Implémentation de `IDao`

```java
package dao;

import org.springframework.stereotype.Repository;

@Repository("dao")
public class IDaoImpl implements IDao {
    @Override
    public double getData() {
        return 20;
    }
}

```

---

## 3. Interface `IMetier` (Service Métier)

```java
package metier;

public interface IMetier {
    double calcul();
}

```

---

## 4. Implémentation de `IMetier` avec couplage faible

```java
package metier;

import dao.IDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("metier")
public class IMetierImpl implements IMetier {
    @Autowired
    private IDao dao ;

    public IMetierImpl() {
    }

    //injection statique via le constructeur
    public IMetierImpl(IDao dao) {
        this.dao = dao ;
    }
    @Override
    public double calcul() {
        return dao.getData() * 3;
    }

    //injection statique via le setter
    public void setDao(IDao dao) {
        this.dao = dao;
    }
}

```

---

## 5. Injection de dépendances

### a. **Injection par instanciation statique**

```java
package presentation;


import dao.IDao;
import dao.IDaoImpl;
import metier.IMetier;
import metier.IMetierImpl;

/**
 * Instanciation statique via le constructeur
 **/
public class PresentationV1 {
    public static void main(String[] args) {
        IDao dao = new IDaoImpl();
        IMetier metier = new IMetierImpl(dao);
        System.out.println(metier.calcul());
    }
}

```

### b. **Injection par instanciation dynamique**

```java
package presentation;

import dao.IDao;
import metier.IMetier;

import java.io.File;
import java.util.Scanner;


/**
 * Instanciation dynamique via un fichier .txt
 **/

public class PresentationV2 {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(new File("config.txt"));
            String daoClassName = sc.nextLine();
            System.out.println(daoClassName);

            Class<?> cDao = Class.forName(daoClassName);
            IDao dao = (IDao) cDao.getConstructor().newInstance();

            String metierClassName = sc.nextLine();
            System.out.println(metierClassName);
            Class<?> cMetier = Class.forName(metierClassName);
            IMetier metier = (IMetier) cMetier.getConstructor(IDao.class).newInstance(dao);

            System.out.println(metier.calcul());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

```

### c. **Injection avec Spring (Version XML)**

#### Fichier `config.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE beans PUBLIC "-//SPRING//DTD BEAN 2.0//EN"
        "http://www.springframework.org/dtd/spring-beans-2.0.dtd">
<beans>
    <bean id="dao" class="dao.IDaoImpl"></bean>
    <bean id="metier" class="metier.IMetierImpl">
        <property name="dao" ref="dao"></property>
    </bean>
</beans>
```

#### Fichier `PresentationSpringXML.java`

```java
package presentation;

import metier.IMetier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Instanciation dynamique version XML en utilisant Spring
 **/

public class PresentationV3 {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
        IMetier metier= (IMetier) context.getBean("metier");
        System.out.println(metier.calcul());
    }
}

```

### d. **Injection avec Spring (Version Annotations)**

#### Fichier `PresentationSpringAnnotations.java`

```java
public class PresentationV4 {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("dao","metier");
        IMetier metier = (IMetier) context.getBean("metier");
        System.out.println(metier.calcul());
    }
}
```

---

## Configuration Maven (pom.xml)

Ce projet utilise Maven pour la gestion des dépendances. Assure-toi que ton fichier `pom.xml` contient les dépendances nécessaires pour Spring, comme ceci :

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.enset.sdia</groupId>
    <artifactId>jee-spring-app</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-core</artifactId>
            <version>5.3.23</version> <!-- Utilise la dernière version stable -->
        </dependency>

        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.3.23</version> <!-- Utilise la dernière version stable -->
        </dependency>

        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-beans</artifactId>
            <version>5.3.23</version> <!-- Utilise la dernière version stable -->
        </dependency>


    </dependencies>

</project>
```

---

## Exécution

### 1. Injection statique : `PresentationStatic`
### 2. Injection dynamique : `PresentationDynamic`
### 3. Injection avec Spring XML : `PresentationSpringXML`
### 4. Injection avec Spring Annotations : `PresentationSpringAnnotations`

---

## Conclusion

Ce projet démontre différentes façons d'injecter des dépendances dans un projet Java en utilisant Spring. Il met en œuvre l'injection de dépendances à la fois par instanciation manuelle (statique et dynamique) et avec Spring, à la fois via XML et annotations.
