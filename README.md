# Sistema de Clasificación ABC en Java

🎓 **Proyecto universitario**  
Desarrollado en la Universidad Abierta y a Distancia de México (UnADM), durante el semestre 2025-1.

Este sistema aplica el análisis ABC a productos en inventario, clasificándolos en categorías **A**, **B** y **C** con base en su **costo unitario**. Es una herramienta útil para identificar los productos más relevantes en términos de valor económico.

## 📌 ¿Qué es el análisis ABC?

El análisis ABC es una técnica de clasificación utilizada en la gestión de inventarios. Se basa en el principio de Pareto (80/20) y divide los productos en tres grupos:

- **A**: Productos de alto valor. Representan un pequeño porcentaje de los productos pero una gran parte del valor total.
- **B**: Productos de valor intermedio.
- **C**: Productos de bajo valor. Son numerosos pero tienen poco impacto económico.

## ✅ Funcionalidades

- Registro de productos con nombre del artículo, cantidad y costo unitario.
- Cálculo automático del valor total por producto.
- Clasificación automática en A, B y C según su costo.
- Ordenamiento descendente por valor.
- Visualización de productos clasificados en una **tabla principal**.
- Resumen de la clasificación ABC (por cantidad y valor) en una **segunda tabla**.
- Interfaz gráfica desarrollada con Java Swing.

## 🛠️ Tecnologías usadas

- Java (versión recomendada: Java 8.2)
- Swing (interfaz gráfica)
- IDE sugerido: NetBeans

## 📂 Estructura del proyecto

AnalisisABC/
├── Source Packages/
│ └── Interfaces/
│ ├── InventarioABC.java # Clase principal con la interfaz gráfica
│ └── Producto.java # Clase que representa cada producto
├── README.md
└── .gitignore


## ▶️ Cómo ejecutar

1. Abre el proyecto en tu IDE Java preferido (Eclipse, NetBeans, IntelliJ).
2. Asegúrate de tener Java 8.2 o superior instalado.
3. Ejecuta la clase `InventarioABC` como aplicación Java.
4. La ventana se abrirá y podrás comenzar a registrar productos.

## 📅 Fecha

Proyecto desarrollado en marzo de 2025.

## 👨‍💻 Autor

José Ángel Trevilla León  

## 📄 Licencia

Este proyecto se distribuye bajo la licencia MIT. Puedes usarlo, modificarlo y compartirlo libremente, siempre dando crédito al autor original.

