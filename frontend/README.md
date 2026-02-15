# FoodLink Frontend

Next.js 14 application with Tailwind CSS for the FoodLink food recommendation platform.

## 🚀 Quick Start

```bash
# Install dependencies
npm install

# Run development server
npm run dev
```

Open [http://localhost:3000](http://localhost:3000)

## 📁 Project Structure

```
frontend/
├── src/
│   ├── app/              # Next.js App Router pages
│   │   ├── [slug]/       # Creator profile pages
│   │   ├── auth/         # Auth pages (login/register)
│   │   ├── dashboard/    # Creator dashboard
│   │   ├── layout.tsx
│   │   └── page.tsx      # Home page
│   ├── components/       # Reusable components
│   └── lib/              # Utilities and API client
├── public/               # Static assets
├── package.json
└── tailwind.config.ts
```

## 🎨 Features

- **Public Pages:**
  - Landing page
  - Creator profile pages with search
  - Responsive design

- **Creator Features:**
  - Authentication (login/register)
  - Dashboard to manage recommendations
  - Analytics tracking

## 🛠️ Tech Stack

- **Framework:** Next.js 14 (App Router)
- **Language:** TypeScript
- **Styling:** Tailwind CSS
- **State Management:** Zustand
- **Data Fetching:** React Query
- **HTTP Client:** Axios

## 🔧 Environment Variables

Create a `.env.local` file:

```bash
NEXT_PUBLIC_API_URL=http://localhost:8080/api/v1
```

## 📝 Available Scripts

```bash
npm run dev      # Start development server
npm run build    # Build for production
npm run start    # Start production server
npm run lint     # Run ESLint
```

## 🎯 Key Pages

- `/` - Landing page
- `/[slug]` - Creator profile page (e.g., `/delhifoodie`)
- `/auth/login` - Creator login
- `/auth/register` - Creator registration
- `/dashboard` - Creator dashboard

## 🔗 API Integration

The frontend connects to the Spring Boot backend at `http://localhost:8080/api/v1`

See `src/lib/api.ts` for all API functions.

## 🚢 Deployment

### Vercel (Recommended)

```bash
# Install Vercel CLI
npm i -g vercel

# Deploy
vercel
```

### Build for Production

```bash
npm run build
npm run start
```

## 📱 Responsive Design

- Mobile-first approach
- Breakpoints: sm (640px), md (768px), lg (1024px), xl (1280px)

## 🎨 Customization

### Colors

Edit `tailwind.config.ts` to customize the theme:

```typescript
colors: {
  primary: {...},
  // Add your colors
}
```

### Instagram Gradient

Use the `bg-instagram-gradient` class for Instagram-style gradients.

## 🐛 Troubleshooting

**API connection errors:**
- Ensure backend is running on port 8080
- Check CORS settings in backend
- Verify `NEXT_PUBLIC_API_URL` in `.env.local`

**Build errors:**
- Clear `.next` folder: `rm -rf .next`
- Delete `node_modules` and reinstall: `rm -rf node_modules && npm install`

## 📚 Learn More

- [Next.js Documentation](https://nextjs.org/docs)
- [Tailwind CSS](https://tailwindcss.com/docs)
- [React Query](https://tanstack.com/query/latest)
