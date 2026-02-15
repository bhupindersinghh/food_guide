import Link from "next/link";

export default function Home() {
  return (
    <div className="min-h-screen">
      {/* Hero Section */}
      <section className="bg-gradient-to-r from-pink-500 via-red-500 to-yellow-500 text-white">
        <div className="container mx-auto px-4 py-20 text-center">
          <h1 className="text-5xl font-bold mb-6">
            Discover Food Recommendations
            <br />
            From Your Favorite Creators
          </h1>
          <p className="text-xl mb-8 opacity-90">
            Search, explore, and find the best restaurants recommended by food creators
          </p>
          <div className="flex gap-4 justify-center">
            <Link
              href="/creators"
              className="bg-white text-pink-600 px-8 py-3 rounded-full font-semibold hover:bg-gray-100 transition"
            >
              Explore Creators
            </Link>
            <Link
              href="/auth/login"
              className="bg-transparent border-2 border-white px-8 py-3 rounded-full font-semibold hover:bg-white hover:text-pink-600 transition"
            >
              I'm a Creator
            </Link>
          </div>
        </div>
      </section>

      {/* Features Section */}
      <section className="py-16">
        <div className="container mx-auto px-4">
          <h2 className="text-3xl font-bold text-center mb-12">How It Works</h2>
          <div className="grid md:grid-cols-3 gap-8">
            <div className="text-center">
              <div className="w-16 h-16 bg-pink-100 rounded-full flex items-center justify-center mx-auto mb-4">
                <span className="text-2xl">👤</span>
              </div>
              <h3 className="text-xl font-semibold mb-2">Follow Creators</h3>
              <p className="text-gray-600">
                Find your favorite food creators and explore their recommendations
              </p>
            </div>
            <div className="text-center">
              <div className="w-16 h-16 bg-pink-100 rounded-full flex items-center justify-center mx-auto mb-4">
                <span className="text-2xl">🔍</span>
              </div>
              <h3 className="text-xl font-semibold mb-2">Search Dishes</h3>
              <p className="text-gray-600">
                Search for specific dishes, restaurants, or areas
              </p>
            </div>
            <div className="text-center">
              <div className="w-16 h-16 bg-pink-100 rounded-full flex items-center justify-center mx-auto mb-4">
                <span className="text-2xl">🍽️</span>
              </div>
              <h3 className="text-xl font-semibold mb-2">Discover Food</h3>
              <p className="text-gray-600">
                Get direct links to Instagram reels and Google Maps
              </p>
            </div>
          </div>
        </div>
      </section>

      {/* CTA Section */}
      <section className="bg-gray-100 py-16">
        <div className="container mx-auto px-4 text-center">
          <h2 className="text-3xl font-bold mb-4">Are you a food creator?</h2>
          <p className="text-xl text-gray-600 mb-8">
            Help your audience find your recommendations easily
          </p>
          <Link
            href="/auth/register"
            className="bg-instagram-gradient text-white px-8 py-3 rounded-full font-semibold hover:opacity-90 transition inline-block"
          >
            Get Started for Free
          </Link>
        </div>
      </section>
    </div>
  );
}
