## 🧾 Point of Sale (POS) System

A modern, full-stack **POS (Point of Sale) System** built using **Spring Boot** for the backend and **React.js** (with **shadcn/ui**) for the frontend.
The system helps manage **sales, products, inventory, and online payments** through **Razorpay** integration.

---

### 🚀 Tech Stack

#### **Frontend**

- ⚛️ **React.js** — Component-based UI framework
- 🎨 **shadcn/ui** — Beautiful, accessible UI components
- ⚡ **Axios** — For communicating with the backend REST API
- 🧭 **React Router** — For page routing
- 💳 **Razorpay Checkout** — For secure online payments

#### **Backend**

- ☕ **Spring Boot (Java)** — RESTful API and business logic
- 🧩 **Spring Data JPA** — Database persistence layer
- 🐘 **PostgreSQL / MySQL** — Relational database
- 🔐 **Spring Security** — For authentication and authorization

---

### 🧠 Core Features

- 🛍️ **Product Management**

  - Add, update, delete, and view products
  - Category and stock management

- 🧾 **Billing & Checkout**

  - Create customer bills
  - Apply discounts, taxes, and calculate totals

- 💳 **Online Payment Integration**

  - Integrated **Razorpay API** for card/UPI payments
  - Real-time payment confirmation

- 📦 **Inventory Tracking**

  - Update stock automatically after each sale
  - Low-stock alerts

- 📊 **Sales Dashboard**

  - Visual reports for daily, weekly, and monthly sales
  - Performance analytics

- 👥 **User Management**

  - Role-based access (Admin, Cashier, Manager)
  - Secure login and registration

---

### 🧰 Project Structure

```
pos-system/
│
├── backend/ (Spring Boot)
│   ├── src/main/java/com/pos/
│   │   ├── controller/
│   │   ├── model/
│   │   ├── repository/
│   │   ├── service/
│   │   └── config/
│   └── src/main/resources/
│       └── application.properties
│
├── frontend/ (React + shadcn/ui)
│   ├── src/
│   │   ├── components/
│   │   ├── pages/
│   │   ├── hooks/
│   │   ├── services/
│   │   └── App.jsx
│   └── package.json
│
└── README.md
```

---

### ⚙️ Setup Instructions

#### **1. Backend Setup**

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

Backend will start on **[http://localhost:8080](http://localhost:8080)**

#### **2. Frontend Setup**

```bash
cd frontend
npm install
npm run dev
```

Frontend will start on **[http://localhost:5173](http://localhost:5173)**

#### **3. Razorpay Setup**

Create a Razorpay account → get your API keys →
add them to your backend `application.properties`:

```properties
razorpay.key_id=YOUR_KEY_ID
razorpay.key_secret=YOUR_KEY_SECRET
```

---

### 🔒 Environment Variables

Create a `.env` file inside `frontend/`:

```env
VITE_API_BASE_URL=http://localhost:8080/api
VITE_RAZORPAY_KEY_ID=YOUR_KEY_ID
```

---

### 📸 Screenshots (optional)

| Page         | Preview                       |
| ------------ | ----------------------------- |
| Dashboard    | 📊 Sales overview             |
| POS Screen   | 💳 Product list & cart        |
| Payment Page | Razorpay Checkout integration |

---

### 🧪 Future Enhancements

- 🧍 Customer loyalty and reward system
- 📱 PWA (mobile support)
- 📦 Barcode scanner integration
- ☁️ Cloud-based deployment (AWS / Render)

---

### 👨‍💻 Author

**Your Name**
📧 [shukrullah.shirzad.779@gmail.com](mailto:shukrullah.shirzad.779@gmail.com)
🔗 [LinkedIn](#) | [GitHub](#)
